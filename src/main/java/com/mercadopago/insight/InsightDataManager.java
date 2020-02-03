package com.mercadopago.insight;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;

import javax.net.ssl.SSLPeerUnverifiedException;

import com.google.gson.Gson;
import com.mercadopago.MercadoPago;
import com.mercadopago.insight.dto.BusinessFlowInfo;
import com.mercadopago.insight.dto.ClientInfo;
import com.mercadopago.insight.dto.ConnectionInfo;
import com.mercadopago.insight.dto.DeviceInfo;
import com.mercadopago.insight.dto.EventInfo;
import com.mercadopago.insight.dto.ProtocolHttp;
import com.mercadopago.insight.dto.ProtocolInfo;
import com.mercadopago.insight.dto.StructuredMetricRequest;
import com.mercadopago.insight.dto.TcpInfo;
import com.mercadopago.insight.dto.TrafficLightRequest;
import com.mercadopago.insight.dto.TrafficLightResponse;
import com.mercadopago.net.KeepAliveStrategy;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.util.EntityUtils;

public class InsightDataManager {

    static final String INSIGHT_DEFAULT_BASE_URL = "https://events.mercadopago.com/v2/";
    static final String HEADER_X_INSIGHTS_BUSINESS_FLOW = "X-Insights-Business-Flow";
    public static final String HEADER_X_INSIGHTS_EVENT_NAME = "X-Insights-Event-Name";
    static final String HEADER_X_INSIGHTS_METRIC_LAB_SCOPE = "X-Insights-Metric-Lab-Scope";
    static final String HEADER_X_INSIGHTS_DATA_ID = "X-Insights-Data-Id";
    static final String HEADER_X_PRODUCT_ID = "X-Product-Id";
    static final String HEADER_ACCEPT_TYPE = "Accept";
    static final String INSIGHTS_API_ENDPOINT_TRAFFIC_LIGHT = "traffic-light";
    static final String INSIGHTS_API_ENDPOINT_METRIC = "metric";

    private static final int DEFAULT_TTL = 600;
    private static final int DEFAULT_MAX_CONNECTIONS = 10;
    private static final int DEFAULT_CONNECTION_TIMEOUT_MS = 3000;
    private static final int VALIDATE_INACTIVITY_INTERVAL_MS = 30000;
    private static final int DEFAULT_CONNECTION_REQUEST_TIMEOUT_MS = 5000;
    private static final int DEFAULT_SOCKET_TIMEOUT_MS = 5000;

    private static HttpClient restClient;
    private static TrafficLightResponse trafficLight;
    private static long sendDataDeadlineMillis = Long.MIN_VALUE;
    private static InsightDataManager insightDataManager = null;
    private static String osName;
    private static String deviceRam;
    private static String cpuType;

    public static InsightDataManager getInstance() {
        if (insightDataManager == null) {
            insightDataManager = new InsightDataManager();
        }
        return insightDataManager;
    }

    private InsightDataManager() {
        restClient = createHttpClient();
        initializeDeviceInfo();
        HttpResponse response = callTrafficLight();
        EntityUtils.consumeQuietly(response.getEntity());
    }

    /**
     * Method to ask the back-end if the client should send Insights Data (or not).
     * 
     * @return HttpResponse
     */
    private HttpResponse callTrafficLight() {

        HttpResponse lightResponse;
        HttpPost request = new HttpPost(INSIGHT_DEFAULT_BASE_URL + INSIGHTS_API_ENDPOINT_TRAFFIC_LIGHT);

        try {

            request.addHeader(HEADER_X_INSIGHTS_METRIC_LAB_SCOPE, MercadoPago.SDK.getMetricsScope());
            request.setHeader(HEADER_ACCEPT_TYPE, ContentType.APPLICATION_JSON.toString());
            request.setHeader(HTTP.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());

            ClientInfo clientInfo = new ClientInfo.Builder()
                .withName(MercadoPago.SDK.getClientName())
                .withVersion(MercadoPago.SDK.getVersion())
                .build();

            TrafficLightRequest trafficLightRequest = new TrafficLightRequest.Builder()
                .withClientInfo(clientInfo)
                .build();

            Gson gson = new Gson();
            String requestJson = gson.toJson(trafficLightRequest);
            StringEntity entityReq = new StringEntity(requestJson, "UTF-8");
            request.setEntity(entityReq);

            HttpRequestBase requestBase = createHttpRequest(request);
            lightResponse = restClient.execute(requestBase);

            HttpEntity entityRes = lightResponse.getEntity();
            if (entityRes != null) {
                trafficLight = new Gson().fromJson(EntityUtils.toString(entityRes), TrafficLightResponse.class);
            } else {
                getDefaultResponse();
            }

        } catch (ClientProtocolException e) {
            lightResponse = new BasicHttpResponse(new BasicStatusLine(request.getProtocolVersion(), 400, null));
            getDefaultResponse();
        } catch (SSLPeerUnverifiedException e) {
            lightResponse = new BasicHttpResponse(new BasicStatusLine(request.getProtocolVersion(), 403, null));
            getDefaultResponse();
        } catch (IOException e) {
            lightResponse = new BasicHttpResponse(new BasicStatusLine(request.getProtocolVersion(), 404, null));
            getDefaultResponse();
        } catch (Exception e) {
            lightResponse = new BasicHttpResponse(new BasicStatusLine(request.getProtocolVersion(), 500, e.getMessage()));
            getDefaultResponse();
        }

        sendDataDeadlineMillis = System.currentTimeMillis() + (Math.abs(trafficLight.getSendTTL()) * 1000);
        return lightResponse;
    }

    /**
     * Method to receive Insights Data from the client.
     * 
     * @param context
     * @param request
     * @param response
     * @param startMillis
     * @param endMillis
     * @param startRequestMillis
     * @return HttpResponse
     */
    public HttpResponse sendInsightMetrics(HttpCoreContext context, HttpRequestBase request, HttpResponse response, long startMillis, long endMillis, long startRequestMillis) {

        HttpResponse insightResponse;
        HttpPost insightRequest = new HttpPost(INSIGHT_DEFAULT_BASE_URL + INSIGHTS_API_ENDPOINT_METRIC);

        try {
            insightRequest.addHeader(HEADER_X_INSIGHTS_METRIC_LAB_SCOPE, MercadoPago.SDK.getMetricsScope());
            insightRequest.setHeader(HEADER_ACCEPT_TYPE, ContentType.APPLICATION_JSON.toString());
            insightRequest.setHeader(HTTP.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());

            ClientInfo clientInfo = new ClientInfo.Builder()
                .withName(MercadoPago.SDK.getClientName())
                .withVersion(MercadoPago.SDK.getVersion())
                .build();

            BusinessFlowInfo businessFlowInfo = null;
            String businessFlowID = request.getLastHeader(HEADER_X_PRODUCT_ID) != null ? request.getLastHeader(HEADER_X_PRODUCT_ID).getValue(): "";
            String businessFlowName = request.getLastHeader(HEADER_X_INSIGHTS_BUSINESS_FLOW) != null ? request.getLastHeader(HEADER_X_INSIGHTS_BUSINESS_FLOW).getValue(): "";
            if (!isNullOrEmpty(businessFlowID) || !isNullOrEmpty(businessFlowName)) {
                businessFlowInfo = new BusinessFlowInfo.Builder()
                    .withName(businessFlowName)
                    .withUID(businessFlowID)
                    .build();
            }

            EventInfo eventInfo = null;
            String eventName = request.getLastHeader(HEADER_X_INSIGHTS_EVENT_NAME) != null ? request.getLastHeader(HEADER_X_INSIGHTS_EVENT_NAME).getValue(): "";
            if (!isNullOrEmpty(eventName)) {
                eventInfo = new EventInfo.Builder()
                    .withName(eventName)
                    .build();
            }

            ProtocolHttp.Builder protocolHttpBuilder = new ProtocolHttp.Builder()
                .withRequestUrl(request.getURI().toString())
                .withResponseCode(response.getStatusLine().getStatusCode())
                .withRequestMethod(request.getMethod())
                .withFirstByteTime(startMillis - startRequestMillis)
                .withLastByteTime(endMillis - startMillis);

            if (request.getAllHeaders() != null) {
                for (Header header : request.getAllHeaders()) {
                    if (header.getName().equalsIgnoreCase(HEADER_X_INSIGHTS_DATA_ID)) {
                        continue;
                    }
                    if (header.getName().equalsIgnoreCase(HTTP.USER_AGENT)) {
                        continue;
                    }
                    protocolHttpBuilder.addRequestHeader(header.getName(), header.getValue());
                }
            }

            if (response.getAllHeaders() != null) {
                for (Header header : response.getAllHeaders()) {
                    protocolHttpBuilder.addResponseHeader(header.getName(), header.getValue());
                }
            }

            ProtocolInfo protocolInfo = new ProtocolInfo.Builder()
                .withName("http")
                .withProtocolHttp(protocolHttpBuilder.build())
                .withRetryCount(getRetryCount(context))
                .build();

            TcpInfo tcpInfo = new TcpInfo.Builder()
                .withSourceAddress(getHostAddress())
                .build();

            ConnectionInfo.Builder connectionInfoBuilder = new ConnectionInfo.Builder()
                .withProtocolInfo(protocolInfo)
                .withTcpInfo(tcpInfo)
                .withCompleteData(endMillis > 0);

            if (request.getLastHeader(HTTP.USER_AGENT) != null) {
                connectionInfoBuilder.withUserAgent(request.getLastHeader(HTTP.USER_AGENT).getValue());
            }

            DeviceInfo deviceInfo = null;
            if(!isNullOrEmpty(osName) || !isNullOrEmpty(deviceRam) || !isNullOrEmpty(cpuType)){
                deviceInfo = new DeviceInfo.Builder()
                    .withCpuType(cpuType)
                    .withOsName(osName)
                    .withRamSize(deviceRam)
                    .build();
            }

            StructuredMetricRequest structuredMetricRequest = new StructuredMetricRequest.Builder()
                .withEventInfo(eventInfo)
                .withClientInfo(clientInfo)
                .withBusinessFlowInfo(businessFlowInfo)
                .withConnectionInfo(connectionInfoBuilder.build())
                .withDeviceInfo(deviceInfo)
                .build();

            Gson gson = new Gson();
            String requestJson = gson.toJson(structuredMetricRequest);
            StringEntity entityReq = new StringEntity(requestJson, "UTF-8");
            insightRequest.setEntity(entityReq);

            insightResponse = executeRequest(insightRequest);

        } catch (Exception e) {
            insightResponse = new BasicHttpResponse(new BasicStatusLine(insightRequest.getProtocolVersion(), 500, e.getMessage()));
        }

        return insightResponse;
    }

    public Boolean isInsightMetricsEnable(String url) {
        if (System.currentTimeMillis() > sendDataDeadlineMillis) {
            callTrafficLight();
        }
        if (trafficLight.isSendDataEnabled() && isEndpointInWhiteList(trafficLight, url)) {
            return true;
        }
        return false;
    }

    public TrafficLightResponse getTrafficLightResponse() {
        return trafficLight;
    }

    /**
     * Checks if insight data should be sent for this endpoint
     * <p>
     * Designed to match endpoints against the white list according to the following
     * rules: - the list has only one item and the pattern is a single asterisk:
     * <code>true</code> - the list has one or more values, iterate the list, split
     * the pattern by asterisk, all the patternParts are present on the request:
     * <code>true</code>
     *
     * @param trafficLight The response for the trafficLight with the list of
     *                     endpoints to match
     * @param requestUrl   The endpoint's URL to be checked
     * @return <code>true</code> if the requestUrl matches to the allowed/desired
     *         endpoints, otherwise <code>false</code>.
     */
    public boolean isEndpointInWhiteList(TrafficLightResponse trafficLight, String requestUrl) {
        if (trafficLight.getEndpointWhiteList() == null) {
            return false;
        }

        for (String pattern : trafficLight.getEndpointWhiteList()) {
            if (pattern.equals("*")) {
                return true;
            }

            boolean matched = true;
            String[] parts = pattern.split("\\*");
            for (String part : parts) {
                if (part.length() == 0) {
                    continue;
                }
                matched = matched && requestUrl.toLowerCase().contains(part);
            }
            if (matched) {
                return true;
            }
        }

        return false;
    }

    private void getDefaultResponse() {
        trafficLight = new TrafficLightResponse();
        trafficLight.setSendDataEnabled(false);
        trafficLight.setSendTTL(DEFAULT_TTL);
    }

    private void initializeDeviceInfo(){
        
        int availableCPU = Runtime.getRuntime().availableProcessors();
        String osname = System.getProperty("os.name")!=null?System.getProperty("os.name"):"";
        String osVersion = System.getProperty("os.version")!=null?System.getProperty("os.version"):"";
        String modelName = System.getProperty("os.arch")!=null?System.getProperty("os.arch"):"";
        String ram = getMemorySize();

        if(!isNullOrEmpty(ram)){
            deviceRam = ram;
        }

        if(!isNullOrEmpty(osName + " "+ osVersion)){
            osName =osname + " "+ osVersion;
        }

        if(!isNullOrEmpty(modelName)){
            cpuType = modelName;
            if(availableCPU!=0){
                cpuType += " - " + availableCPU + " core";
            }
        }
    }

    private String getMemorySize() {
        String memorySize = "";
        try {
            long memSize = ((com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize();
            memorySize =  String.valueOf(memSize);
        } catch (Exception e) {
            return memorySize;
        }

        return memorySize;
    }

    private String getHostAddress() {
        InetAddress localhost = null;
        try {
            localhost = InetAddress.getLocalHost();
        } catch (Exception e) {
            // DO nothing
        }
        return localhost != null ? localhost.getHostAddress() : "";
    }

    private int getRetryCount(HttpCoreContext _context) {
        int retry = 1;
        try {
            retry = new Long(_context.getConnection().getMetrics().getRequestCount()).intValue();
        } catch (Exception e) {
            // DO nothing
        }

        return retry - 1;
    }

    /**
     * Create a HttpClient
     * 
     * @return a HttpClient
     */
    private HttpClient createHttpClient() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(DEFAULT_MAX_CONNECTIONS);
        connectionManager.setDefaultMaxPerRoute(MercadoPago.SDK.getMaxConnections());
        connectionManager.setValidateAfterInactivity(VALIDATE_INACTIVITY_INTERVAL_MS);
        DefaultHttpRequestRetryHandler retryHandler = new DefaultHttpRequestRetryHandler(MercadoPago.SDK.getRetries(),
                false);

        HttpClientBuilder httpClientBuilder = HttpClients.custom().setConnectionManager(connectionManager)
                .setKeepAliveStrategy(new KeepAliveStrategy()).setRetryHandler(retryHandler).disableCookieManagement()
                .disableRedirectHandling();

        return httpClientBuilder.build();
    }

    private HttpRequestBase createHttpRequest(HttpPost request) {
        HttpRequestBase requestBase = null;
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom()
            .setSocketTimeout(DEFAULT_SOCKET_TIMEOUT_MS)
            .setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT_MS)
            .setConnectionRequestTimeout(DEFAULT_CONNECTION_REQUEST_TIMEOUT_MS);

        requestBase = request;
        request.setConfig(requestConfigBuilder.build());
        return requestBase;
    }

    private HttpResponse executeRequest(HttpPost request) {
        HttpResponse response;

        try {
            HttpRequestBase requestBase = createHttpRequest(request);
            response = restClient.execute(requestBase);
        } catch (ClientProtocolException e) {
            response = new BasicHttpResponse(new BasicStatusLine(request.getProtocolVersion(), 400, null));
        } catch (SSLPeerUnverifiedException e) {
            response = new BasicHttpResponse(new BasicStatusLine(request.getProtocolVersion(), 403, null));
        } catch (IOException e) {
            response = new BasicHttpResponse(new BasicStatusLine(request.getProtocolVersion(), 404, null));
        }
        return response;
    }

    private boolean isNullOrEmpty(final String whatString) {
        return (whatString == null) || (whatString.trim().isEmpty());
    }

    

}