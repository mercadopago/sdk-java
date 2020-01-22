package com.mercadopago.insight;

import com.mercadopago.insight.dto.TrafficLightResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;

public class Stats extends Thread {

    private HttpRequestBase _httpRequest;
    private HttpResponse _httpResponse;
    private long _startMillis;
    private long _endMillis;

    public Stats(HttpRequestBase request, HttpResponse response) {
        this._httpRequest = request;
        this._httpResponse = response;
        
    }
 
    public Stats(HttpRequestBase request, HttpResponse response, long startMillis, long endMillis) {
        this._httpRequest = request;
        this._httpResponse = response;
        this._startMillis = startMillis;
        this._endMillis = endMillis;
	}

	@Override
    public void run() {
        TrafficLightResponse trafficLight = TrafficLightManager.getInstance().trafficLightResponse;
        if (trafficLight.isSendDataEnabled() && isEndpointInWhiteList(trafficLight, this._httpRequest.getURI().toString())) {
            InsightDataManager insightDataManager = new  InsightDataManager();
            insightDataManager.call(this._httpRequest, this._httpResponse, this._startMillis, this._endMillis);
        }
    }

    private boolean isEndpointInWhiteList(TrafficLightResponse trafficLight, String endpoint) {
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
                matched = matched && endpoint.toLowerCase().contains(part);
            }
            if (matched) {
                return true;
            }
        }

        return false;
    }
}