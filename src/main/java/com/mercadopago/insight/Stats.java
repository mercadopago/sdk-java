package com.mercadopago.insight;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.util.EntityUtils;

public class Stats extends Thread {

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
        InsightDataManager insightDataManager = InsightDataManager.getInstance();
        if (insightDataManager.isInsightMetricsEnable(this._httpRequest.getURI().toString())) {
            HttpResponse response = insightDataManager.sendInsightMetrics(this._context, this._httpRequest, this._httpResponse, this._startMillis, this._endMillis, this._startRequestMillis);
            EntityUtils.consumeQuietly(response.getEntity());
        }
    }
}