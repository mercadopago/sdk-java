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
        if (trafficLight.isSendDataEnabled()) {
            InsightDataManager insightDataManager = new  InsightDataManager();
            insightDataManager.call(this._httpRequest, this._httpResponse);
        }
    }

  } 