package com.mercadopago.insight;

import java.io.IOException;

import com.mercadopago.MercadoPago;
import com.mercadopago.insight.dto.ClientInfo;
import com.mercadopago.insight.dto.TrafficLightRequest;
import com.mercadopago.insight.dto.TrafficLightResponse;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.google.gson.Gson;

public class TrafficLightManager {

    public static final String HEADER_X_INSIGHTS_BUSINESS_FLOW = "X-Insights-Business-Flow";
    public static final String HEADER_X_INSIGHTS_EVENT_NAME = "X-Insights-Event-Name";

    static final String HEADER_X_INSIGHTS_METRIC_LAB_SCOPE = "X-Insights-Metric-Lab-Scope";
    static final String HEADER_X_INSIGHTS_DATA = "X-Insights-Data";
    static final String HEADER_X_INSIGHTS_DATA_ID = "X-Insights-Data-Id";
    static final String HEADER_X_PRODUCT_ID = "X-Product-Id";
    static final String HEADER_USER_AGENT = "User-Agent";
    static final String HEADER_CONTENT_TYPE = "Content-Type";

    static final String INSIGHTS_API_BASE_PATH = "v2";
    static final String INSIGHTS_API_ENDPOINT_TRAFFIC_LIGHT = "traffic-light";
    static final String INSIGHTS_API_ENDPOINT_METRIC = "metric";

    // static variable single_instance of type Singleton 
    private static TrafficLightManager trafficLightManager = null; 
  
    // variable of type String 
    public TrafficLightResponse trafficLightResponse; 

    private static long sendDataDeadlineMillis = Long.MIN_VALUE;
  
    // private constructor restricted to this class itself 
    private TrafficLightManager() 
    { 
        call();
    } 
  
    // static method to create instance of Singleton class 
    public static synchronized TrafficLightManager getInstance() 
    { 
        if (trafficLightManager == null) {
            trafficLightManager = new TrafficLightManager(); 
        }else if (System.currentTimeMillis() > sendDataDeadlineMillis){
            trafficLightManager = new TrafficLightManager(); 
        }

        return trafficLightManager; 
    } 

    private void call() {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {

            HttpPost request = new HttpPost("https://events.mercadopago.com/v2/traffic-light");

            // add request headers
            request.addHeader(HEADER_X_INSIGHTS_DATA, INSIGHTS_API_ENDPOINT_TRAFFIC_LIGHT);
            request.addHeader("X-Insights-Metric-Lab-Scope", "test");
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");

            ClientInfo clientInfo = new ClientInfo.Builder().withName(MercadoPago.SDK.getClientName()).withVersion(MercadoPago.SDK.getVersion()).build();
            TrafficLightRequest trafficLightRequest = new TrafficLightRequest.Builder().withClientInfo(clientInfo).build();
            Gson gson = new Gson();
            String requestJson =  gson.toJson(trafficLightRequest);
            StringEntity entityReq = new StringEntity(requestJson, "UTF-8");
            request.setEntity(entityReq);

            CloseableHttpResponse lightResponse = httpClient.execute(request);

            try {

                HttpEntity entityRes = lightResponse.getEntity();
                if (entityRes != null) {
                    // return it as a String
                    String result = EntityUtils.toString(entityRes);
                    this.trafficLightResponse = new Gson().fromJson(result, TrafficLightResponse.class);
                    sendDataDeadlineMillis = System.currentTimeMillis() + (Math.abs(this.trafficLightResponse.getSendTTL()) * 1000);
                }else {
                    getDefaultResponse();
                }

            } finally {
                lightResponse.close();
            }
        } catch (Exception e) {
            getDefaultResponse();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                getDefaultResponse();
            }
        }

    }
    
    private void getDefaultResponse(){
        this.trafficLightResponse = new TrafficLightResponse();
        this.trafficLightResponse.setSendDataEnabled(false);
    }
}

