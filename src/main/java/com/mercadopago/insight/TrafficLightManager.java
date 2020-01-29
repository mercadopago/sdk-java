package com.mercadopago.insight;

import com.google.gson.Gson;
import com.mercadopago.MercadoPago;
import com.mercadopago.insight.dto.ClientInfo;
import com.mercadopago.insight.dto.TrafficLightRequest;
import com.mercadopago.insight.dto.TrafficLightResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class TrafficLightManager {

    private static TrafficLightResponse trafficLightResponse;
    private static InsightConnectionManager restClient = new InsightConnectionManager();

    private static long sendDataDeadlineMillis = Long.MIN_VALUE;

    // static method to create instance of Singleton class
    public static synchronized TrafficLightResponse getInstance() {
        if (trafficLightResponse == null || (System.currentTimeMillis() > sendDataDeadlineMillis)) {
            callTrafficLight();
        }

        return trafficLightResponse;
    }

    private static HttpResponse callTrafficLight() {
        
        HttpResponse lightResponse;
        HttpPost request = new HttpPost(Stats.INSIGHT_DEFAULT_BASE_URL + Stats.INSIGHTS_API_BASE_PATH +"/"+ Stats.INSIGHTS_API_ENDPOINT_TRAFFIC_LIGHT);

        try {
            request.addHeader(Stats.HEADER_X_INSIGHTS_DATA, Stats.INSIGHTS_API_ENDPOINT_TRAFFIC_LIGHT);
            request.addHeader(Stats.HEADER_X_INSIGHTS_METRIC_LAB_SCOPE, MercadoPago.SDK.getMetricsScope());
            request.setHeader(Stats.HEADER_ACCEPT_TYPE, ContentType.APPLICATION_JSON.toString());
            request.setHeader(HTTP.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());

            ClientInfo clientInfo = new ClientInfo.Builder().withName(MercadoPago.SDK.getClientName()).withVersion(MercadoPago.SDK.getVersion()).build();
            TrafficLightRequest trafficLightRequest = new TrafficLightRequest.Builder().withClientInfo(clientInfo).build();
            Gson gson = new Gson();
            String requestJson =  gson.toJson(trafficLightRequest);
            StringEntity entityReq = new StringEntity(requestJson, "UTF-8");
            request.setEntity(entityReq);

            lightResponse =  restClient.executeRequest(request);
           
            HttpEntity entityRes = lightResponse.getEntity();
            if (entityRes != null) {
                trafficLightResponse = new Gson().fromJson(EntityUtils.toString(entityRes), TrafficLightResponse.class);
                sendDataDeadlineMillis = System.currentTimeMillis() + (Math.abs(trafficLightResponse.getSendTTL()) * 1000);
            }else {
                getDefaultResponse();
            }

        } catch (Exception e) {
            lightResponse = new BasicHttpResponse(new BasicStatusLine(request.getProtocolVersion(), 500, e.getMessage()));
            getDefaultResponse();
        } 

        return lightResponse;
    }
    
    private static void getDefaultResponse(){
        trafficLightResponse = new TrafficLightResponse();
        trafficLightResponse.setSendDataEnabled(false);
    }
}

