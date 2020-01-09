package com.mercadopago.insight;

import java.io.IOException;

import com.mercadopago.MercadoPago;
import com.mercadopago.insight.dto.BusinessFlowInfo;
import com.mercadopago.insight.dto.ClientInfo;
import com.mercadopago.insight.dto.ConnectionInfo;
import com.mercadopago.insight.dto.EventInfo;
import com.mercadopago.insight.dto.ProtocolHttp;
import com.mercadopago.insight.dto.ProtocolInfo;
import com.mercadopago.insight.dto.StructuredMetricRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.google.gson.Gson;

public class InsightDataManager {

    public void call(HttpRequestBase request, HttpResponse response) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {

            HttpPost insightRequest = new HttpPost("https://events.mercadopago.com/v2/metric");

            // add request headers
            insightRequest.addHeader(TrafficLightManager.HEADER_X_INSIGHTS_METRIC_LAB_SCOPE, "test");
            insightRequest.addHeader(TrafficLightManager.HEADER_X_INSIGHTS_DATA, TrafficLightManager.INSIGHTS_API_ENDPOINT_METRIC);
            insightRequest.setHeader("Accept", "application/json");
            insightRequest.setHeader(TrafficLightManager.HEADER_CONTENT_TYPE, "application/json");
           
            ClientInfo clientInfo = new ClientInfo.Builder().withName(MercadoPago.SDK.getClientName()).withVersion(MercadoPago.SDK.getVersion())
                    .build();
            EventInfo eventInfo = new EventInfo.Builder().withName("test").build();


            BusinessFlowInfo businessFlowInfo = new BusinessFlowInfo.Builder()
                    .withName(request.getLastHeader(TrafficLightManager.HEADER_X_INSIGHTS_BUSINESS_FLOW)!=null?request.getLastHeader(TrafficLightManager.HEADER_X_INSIGHTS_BUSINESS_FLOW).getValue():"")
                    .withUID(request.getLastHeader(TrafficLightManager.HEADER_X_PRODUCT_ID)!=null?request.getLastHeader(TrafficLightManager.HEADER_X_PRODUCT_ID).getValue():"").build();

            ProtocolHttp protocolHttp = new ProtocolHttp.Builder().withRequestUrl(request.getURI().toString())
                                                                .withResponseCode(response.getStatusLine().getStatusCode())
                                                                .withRequestMethod(request.getMethod())
                                                                .build();


            ProtocolInfo protocolInfo = new ProtocolInfo.Builder().withName("http")
                                                                 .withProtocolHttp(protocolHttp)
                                                                 .build();

            ConnectionInfo connectionInfo = new ConnectionInfo.Builder().withUserAgent(request.getLastHeader(TrafficLightManager.HEADER_USER_AGENT)!=null?request.getLastHeader(TrafficLightManager.HEADER_USER_AGENT).getValue():"")
                                                                        .withProtocolInfo(protocolInfo)
                                                                        .withWasReused(false)
                                                                        .build();

            StructuredMetricRequest structuredMetricRequest = new StructuredMetricRequest.Builder()
                                                            .withClientInfo(clientInfo)
                                                            .withEventInfo(eventInfo)
                                                            .withBusinessFlowInfo(businessFlowInfo)
                                                            .withConnectionInfo(connectionInfo)
                                                            .build();

            Gson gson = new Gson();
            String requestJson =  gson.toJson(structuredMetricRequest);
            StringEntity entityReq = new StringEntity(requestJson, "UTF-8");
            insightRequest.setEntity(entityReq);

            CloseableHttpResponse insightResponse = httpClient.execute(insightRequest);

            try {

                HttpEntity entityRes = insightResponse.getEntity();
                if (entityRes != null) {
                    // return it as a String
                    String result = EntityUtils.toString(entityRes);
                    System.out.println(result);
                }

            } finally {
                insightResponse.close();
            }
        } catch (Exception e) {
            System.out.println("SE ROMPIOO TODO METRICAS: " + e.getMessage());
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}