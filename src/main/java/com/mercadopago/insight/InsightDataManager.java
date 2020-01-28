package com.mercadopago.insight;

import java.io.IOException;
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

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpCoreContext;


public class InsightDataManager {

    public HttpResponse sendMetrics(HttpCoreContext context, HttpRequestBase request, HttpResponse response, long startMillis, long endMillis, long startRequestMillis) {
        
        HttpResponse insightResponse;
        HttpPost insightRequest = new HttpPost(Stats.INSIGHT_DEFAULT_BASE_URL + Stats.INSIGHTS_API_BASE_PATH +"/"+ Stats.INSIGHTS_API_ENDPOINT_METRIC);

        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            // add request headers
            insightRequest.addHeader(Stats.HEADER_X_INSIGHTS_METRIC_LAB_SCOPE, MercadoPago.SDK.getMetricsScope());
            insightRequest.addHeader(Stats.HEADER_X_INSIGHTS_DATA, Stats.INSIGHTS_API_ENDPOINT_METRIC);
            insightRequest.setHeader(Stats.HEADER_ACCEPT_TYPE, ContentType.APPLICATION_JSON.toString());
            insightRequest.setHeader(HTTP.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
           
            ClientInfo clientInfo = new ClientInfo.Builder().withName(MercadoPago.SDK.getClientName()).withVersion(MercadoPago.SDK.getVersion()).build();

            BusinessFlowInfo.Builder businessFlowInfoBuilder = new BusinessFlowInfo.Builder()
            .withUID(request.getLastHeader(Stats.HEADER_X_PRODUCT_ID)!=null?request.getLastHeader(Stats.HEADER_X_PRODUCT_ID).getValue():"");
            
            if(request.getLastHeader(Stats.HEADER_X_INSIGHTS_BUSINESS_FLOW)!=null){
                businessFlowInfoBuilder.withName(request.getLastHeader(Stats.HEADER_X_INSIGHTS_BUSINESS_FLOW).getValue());
            }

            EventInfo eventInfo = new EventInfo.Builder().withName(request.getLastHeader(Stats.HEADER_X_INSIGHTS_EVENT_NAME)!=null?request.getLastHeader(Stats.HEADER_X_INSIGHTS_EVENT_NAME).getValue():"Unknown").build();

            ProtocolHttp.Builder protocolHttpBuilder = new ProtocolHttp.Builder().withRequestUrl(request.getURI().toString())
            .withResponseCode(response.getStatusLine().getStatusCode())
            .withRequestMethod(request.getMethod())
            .withFirstByteTime(startMillis-startRequestMillis)
            .withLastByteTime(endMillis-startMillis);
            
            if (request.getAllHeaders() != null) {
                for (Header header: request.getAllHeaders()) {
                    if (header.getName().equalsIgnoreCase(Stats.HEADER_X_INSIGHTS_DATA_ID)) {
                        continue;
                    }
                    if (header.getName().equalsIgnoreCase(HTTP.USER_AGENT)) {
                       continue;
                    }
                    protocolHttpBuilder.addRequestHeader(header.getName(), header.getValue());
                }
            }

            if (response.getAllHeaders() != null) {
                for (Header header: response.getAllHeaders()) {
                    protocolHttpBuilder.addResponseHeader(header.getName(), header.getValue());
                }
            }

            ProtocolInfo protocolInfo = new ProtocolInfo.Builder().withName("http")
            .withProtocolHttp(protocolHttpBuilder.build())
            .withRetryCount(getRetryCount(context))
            .build();

            TcpInfo tcpInfo = new TcpInfo.Builder().withSourceAddress(getHostAddress()).build();

            ConnectionInfo connectionInfo = new ConnectionInfo.Builder().withUserAgent(request.getLastHeader(HTTP.USER_AGENT)!=null?request.getLastHeader(HTTP.USER_AGENT).getValue():"")
            .withProtocolInfo(protocolInfo)
            .withTcpInfo(tcpInfo)
            .withCompleteData(endMillis>0)
            .build();

            DeviceInfo deviceInfo = new DeviceInfo.Builder().withOsName(getOS())
            .withCpuType(System.getProperty("os.arch"))
            .build();

            StructuredMetricRequest structuredMetricRequest = new StructuredMetricRequest.Builder()
            .withEventInfo(eventInfo)
            .withClientInfo(clientInfo)
            .withBusinessFlowInfo(businessFlowInfoBuilder.build())
            .withConnectionInfo(connectionInfo)
            .withDeviceInfo(deviceInfo)
            .build();

            Gson gson = new Gson();
            String requestJson =  gson.toJson(structuredMetricRequest);
            StringEntity entityReq = new StringEntity(requestJson, "UTF-8");
            insightRequest.setEntity(entityReq);

            insightResponse = httpClient.execute(insightRequest);
            httpClient.close();

        } catch (ClientProtocolException e) {
            insightResponse = new BasicHttpResponse(new BasicStatusLine(insightRequest.getProtocolVersion(), 400, null));
        } catch (SSLPeerUnverifiedException e) {
            insightResponse = new BasicHttpResponse(new BasicStatusLine(insightRequest.getProtocolVersion(), 403, null));
        } catch (IOException e) {
            insightResponse = new BasicHttpResponse(new BasicStatusLine(insightRequest.getProtocolVersion(), 404, null));
        }

        return insightResponse;

    }

    private String getHostAddress(){
        InetAddress localhost = null;
        try {
            localhost = InetAddress.getLocalHost();
    
        } catch (Exception e) {
            //DO nothing
        }

       
        return localhost!=null?localhost.getHostAddress():"";
    }

    private String getOS(){
        String osName = System.getProperty("os.name")!=null?System.getProperty("os.name"):"Unknown";
        String osVersion = System.getProperty("os.version")!=null?System.getProperty("os.version"):"Unknown";
        return osName + " "+ osVersion;
    }

    private int getRetryCount(HttpCoreContext _context) {
        int retry = 1;
        try {
            retry = new Long(_context.getConnection().getMetrics().getRequestCount()).intValue();
        } catch (Exception e) {
           //DO nothing
        }
        
        return retry - 1;
    }

}