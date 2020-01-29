package com.mercadopago.insight;

import com.mercadopago.insight.dto.TrafficLightResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpCoreContext;

public class Stats extends Thread {

    static final String INSIGHT_DEFAULT_BASE_URL = "https://events.mercadopago.com/";
    static final String INSIGHTS_API_BASE_PATH = "v2";
    static final String HEADER_X_INSIGHTS_BUSINESS_FLOW = "X-Insights-Business-Flow";
    static final String HEADER_X_INSIGHTS_EVENT_NAME = "X-Insights-Event-Name";
    static final String HEADER_X_INSIGHTS_METRIC_LAB_SCOPE = "X-Insights-Metric-Lab-Scope";
    static final String HEADER_X_INSIGHTS_DATA_ID = "X-Insights-Data-Id";
    static final String HEADER_X_INSIGHTS_DATA = "X-Insights-Data";
    static final String HEADER_X_PRODUCT_ID = "X-Product-Id";
    static final String HEADER_ACCEPT_TYPE = "Accept";
    static final String INSIGHTS_API_ENDPOINT_TRAFFIC_LIGHT = "traffic-light";
    static final String INSIGHTS_API_ENDPOINT_METRIC = "metric";

    private HttpRequestBase _httpRequest;
    private HttpResponse _httpResponse;
    private long _startMillis;
    private long _endMillis;
    private long _startRequestMillis;
    private HttpCoreContext _context;
 
    public Stats(HttpCoreContext context, HttpRequestBase request, HttpResponse response, long startMillis, long endMillis, long startRequestMillis) {
        this._httpRequest = request;
        this._httpResponse = response;
        this._startMillis = startMillis;
        this._endMillis = endMillis;
        this._context = context;
        this._startRequestMillis = startRequestMillis;
	}

	@Override
    public void run() {
        TrafficLightResponse trafficLight = TrafficLightManager.getInstance();
        if (trafficLight.isSendDataEnabled() && isEndpointInWhiteList(trafficLight, this._httpRequest.getURI().toString())) {
            InsightDataManager insightDataManager = new  InsightDataManager();
            insightDataManager.sendMetrics(this._context, this._httpRequest, this._httpResponse, this._startMillis, this._endMillis, this._startRequestMillis);
        }
    }

    public static boolean isEndpointInWhiteList(TrafficLightResponse trafficLight, String endpoint) {
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