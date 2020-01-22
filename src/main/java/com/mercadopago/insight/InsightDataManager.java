package com.mercadopago.insight;

import com.google.gson.Gson;
import com.mercadopago.MercadoPago;
import com.mercadopago.insight.dto.BusinessFlowInfo;
import com.mercadopago.insight.dto.ClientInfo;
import com.mercadopago.insight.dto.ConnectionInfo;
import com.mercadopago.insight.dto.EventInfo;
import com.mercadopago.insight.dto.ProtocolHttp;
import com.mercadopago.insight.dto.ProtocolInfo;
import com.mercadopago.insight.dto.StructuredMetricRequest;
import com.mercadopago.insight.dto.TcpInfo;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.InetAddress;

public class InsightDataManager {

    public void call(HttpRequestBase request, HttpResponse response, long startMillis, long endMillis) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        try {

            HttpPost insightRequest = new HttpPost("https://events.mercadopago.com/v2/metric");

            // add request headers
            insightRequest.addHeader(TrafficLightManager.HEADER_X_INSIGHTS_METRIC_LAB_SCOPE, MercadoPago.SDK.getMetricsScope());
            insightRequest.addHeader(TrafficLightManager.HEADER_X_INSIGHTS_DATA, TrafficLightManager.INSIGHTS_API_ENDPOINT_METRIC);
            insightRequest.setHeader("Accept", "application/json");
            insightRequest.setHeader(TrafficLightManager.HEADER_CONTENT_TYPE, "application/json");
           
            ClientInfo clientInfo = new ClientInfo.Builder().withName(MercadoPago.SDK.getClientName()).withVersion(MercadoPago.SDK.getVersion()).build();

            BusinessFlowInfo businessFlowInfo = new BusinessFlowInfo.Builder()
                                .withName(request.getLastHeader(TrafficLightManager.HEADER_X_INSIGHTS_BUSINESS_FLOW)!=null?request.getLastHeader(TrafficLightManager.HEADER_X_INSIGHTS_BUSINESS_FLOW).getValue():"")
                                .withUID(request.getLastHeader(TrafficLightManager.HEADER_X_PRODUCT_ID)!=null?request.getLastHeader(TrafficLightManager.HEADER_X_PRODUCT_ID).getValue():"").build();
            
            EventInfo eventInfo = new EventInfo.Builder().withName(request.getLastHeader(TrafficLightManager.HEADER_X_INSIGHTS_EVENT_NAME)!=null?request.getLastHeader(TrafficLightManager.HEADER_X_INSIGHTS_EVENT_NAME).getValue():"unknown").build();

            ProtocolHttp.Builder protocolHttpBuilder = new ProtocolHttp.Builder().withRequestUrl(request.getURI().toString())
                                .withResponseCode(response.getStatusLine().getStatusCode())
                                .withRequestMethod(request.getMethod())
                                .withFirstByteTime(startMillis)
                                .withLastByteTime(endMillis);
            
            if (request.getAllHeaders() != null) {
                for (Header header: request.getAllHeaders()) {
                    if (header.getName().equalsIgnoreCase(TrafficLightManager.HEADER_X_INSIGHTS_DATA_ID)) {
                        continue;
                    }
                    if (header.getName().equalsIgnoreCase(TrafficLightManager.HEADER_USER_AGENT)) {
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
                                                                 .build();

            TcpInfo tcpInfo = new TcpInfo.Builder().withSourceAddress(getHostAddress()).build();

            ConnectionInfo connectionInfo = new ConnectionInfo.Builder().withUserAgent(request.getLastHeader(TrafficLightManager.HEADER_USER_AGENT)!=null?request.getLastHeader(TrafficLightManager.HEADER_USER_AGENT).getValue():"")
                                                                        .withProtocolInfo(protocolInfo)
                                                                        .withTcpInfo(tcpInfo)
                                                                        .build();

            StructuredMetricRequest structuredMetricRequest = new StructuredMetricRequest.Builder()
                                                            .withEventInfo(eventInfo)
                                                            .withClientInfo(clientInfo)
                                                            .withBusinessFlowInfo(businessFlowInfo)
                                                            .withConnectionInfo(connectionInfo)
                                                            .build();

            Gson gson = new Gson();
            String requestJson =  gson.toJson(structuredMetricRequest);
            StringEntity entityReq = new StringEntity(requestJson, "UTF-8");
            insightRequest.setEntity(entityReq);

            CloseableHttpResponse insightResponse = httpClient.execute(insightRequest);
            insightResponse.close();

        } catch (Exception e) {
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                //DO nothing
            }
        }

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
}