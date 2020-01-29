package com.mercadopago.insight;

import java.io.IOException;

import javax.net.ssl.SSLPeerUnverifiedException;

import com.mercadopago.MercadoPago;
import com.mercadopago.net.KeepAliveStrategy;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

public class InsightConnectionManager {

    private HttpClient httpClient;

    public InsightConnectionManager() {
        this.httpClient = createHttpClient();
    }

    private static final int DEFAULT_MAX_CONNECTIONS = 10;
    private static final int DEFAULT_CONNECTION_TIMEOUT_MS = 3000;
    private static final int VALIDATE_INACTIVITY_INTERVAL_MS = 30000;
    private static final int DEFAULT_CONNECTION_REQUEST_TIMEOUT_MS = 5000;
    private static final int DEFAULT_SOCKET_TIMEOUT_MS = 5000;

    /**
     * Create a HttpClient
     * 
     * @return a HttpClient
     */
    private HttpClient createHttpClient() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(DEFAULT_MAX_CONNECTIONS);
        connectionManager.setDefaultMaxPerRoute(MercadoPago.SDK.getMaxConnections());
        connectionManager.setValidateAfterInactivity(VALIDATE_INACTIVITY_INTERVAL_MS);
        DefaultHttpRequestRetryHandler retryHandler = new DefaultHttpRequestRetryHandler(MercadoPago.SDK.getRetries(),
                false);

        HttpClientBuilder httpClientBuilder = HttpClients.custom().setConnectionManager(connectionManager)
                .setKeepAliveStrategy(new KeepAliveStrategy()).setRetryHandler(retryHandler).disableCookieManagement()
                .disableRedirectHandling();

        return httpClientBuilder.build();
    }

    private HttpRequestBase createHttpRequest(HttpPost request) {
        HttpRequestBase requestBase = null;
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom()
            .setSocketTimeout(DEFAULT_SOCKET_TIMEOUT_MS)
            .setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT_MS)
            .setConnectionRequestTimeout(DEFAULT_CONNECTION_REQUEST_TIMEOUT_MS);
        
        requestBase = request;
        request.setConfig(requestConfigBuilder.build());
        return requestBase;
    }

    public HttpResponse executeRequest(HttpPost request) {
        HttpResponse response;
        
        try {
            HttpRequestBase requestBase = createHttpRequest(request);
            response= httpClient.execute(requestBase);
        } catch (ClientProtocolException e) {
            response = new BasicHttpResponse(new BasicStatusLine(request.getProtocolVersion(), 400, null));
        } catch (SSLPeerUnverifiedException e) {
            response = new BasicHttpResponse(new BasicStatusLine(request.getProtocolVersion(), 403, null));
        } catch (IOException e) {
            response = new BasicHttpResponse(new BasicStatusLine(request.getProtocolVersion(), 404, null));
        }
		return response;
	}
}