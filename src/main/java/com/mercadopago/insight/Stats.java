package com.mercadopago.insight;

import com.mercadopago.insight.dto.TrafficLightResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;

public class Stats extends Thread {

    private HttpRequestBase _httpRequest;
    private HttpResponse _httpResponse;

    public Stats(HttpRequestBase request, HttpResponse response) {
        this._httpRequest = request;
        this._httpResponse = response;
    }

    @Override
    public void run() {
        TrafficLightResponse trafficLight = TrafficLightManager.getInstance().trafficLightResponse;
        if (trafficLight.isSendDataEnabled() && isEndpointInWhiteList(trafficLight, this._httpRequest.getURI().toString())) {
            InsightDataManager insightDataManager = new  InsightDataManager();
            insightDataManager.call(this._httpRequest, this._httpResponse);
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