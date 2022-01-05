package com.mercadopago;

import com.mercadopago.net.IHttpClient;
import com.mercadopago.net.MPDefaultIHttpClient;
import java.util.logging.Level;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpRequestRetryHandler;

public class MercadoPagoConfig {
    private static final String DEFAULT_BASE_URL = "https://api.mercadopago.com";
    private static final String CURRENT_VERSION = "1.11.0";
    private static final String PRODUCT_ID = "BC32A7VTRPP001U8NHJ0";
    private static final String CLIENT_NAME = "MercadoPago-SDK-Java";
    private static final String TRACKING_ID = String.format("platform:%s,type:SDK%s,so;", getJavaVersion(System.getProperty("java.runtime.version")), CURRENT_VERSION);

    private static final int DEFAULT_MAX_CONNECTIONS = 10;
    private static final int DEFAULT_CONNECTION_TIMEOUT_MS = 20000;
    private static final int DEFAULT_CONNECTION_REQUEST_TIMEOUT_MS = 20000;
    private static final int DEFAULT_SOCKET_TIMEOUT_MS = 20000;
    private static final int DEFAULT_RETRIES = 3;
    private static final String DEFAULT_METRICS_SCOPE = "prod";
    private static final Level DEFAULT_LOGGING_LEVEL = Level.OFF;

    private static volatile String clientSecret = null;
    private static volatile String clientId = null;
    private static volatile String accessToken = null;
    private static volatile String userToken = null;
    private static volatile String appId = null;
    private static volatile String platformId = null;
    private static volatile String corporationId = null;
    private static volatile String integratorId = null;
    private static volatile String baseUrl = getDefaultBaseUrl();
    private static volatile String metricsScope = DEFAULT_METRICS_SCOPE;
    private static volatile Level loggingLevel = DEFAULT_LOGGING_LEVEL;

    private static volatile int maxConnections = DEFAULT_MAX_CONNECTIONS;
    private static volatile int connectionTimeout = DEFAULT_CONNECTION_TIMEOUT_MS;
    private static volatile int connectionRequestTimeout = DEFAULT_CONNECTION_REQUEST_TIMEOUT_MS;
    private static volatile int socketTimeout = DEFAULT_SOCKET_TIMEOUT_MS;
    private static volatile IHttpClient IHttpClient = null;
    private static HttpHost proxy;
    private static volatile MPDefaultIHttpClient mpHttpClient = null;
    private static HttpRequestRetryHandler retryHandler;

    public synchronized static void setHttpClient(IHttpClient otherIHttpClient) {
        IHttpClient = otherIHttpClient;
    }
    public synchronized static MPDefaultIHttpClient getMpHttpClient() {
        if (mpHttpClient == null) {
            mpHttpClient = new MPDefaultIHttpClient();
        }
        return mpHttpClient;
    }

    public synchronized static void setProxy(HttpHost proxyValue){
        proxy = proxyValue;
    }
    public synchronized static HttpHost getProxy() {
        return proxy;
    }

    public static String getProductId() {
        return PRODUCT_ID;
    }

    public static String getTrackingId() {
        return TRACKING_ID;
    }

    public static String getVersion() {
        return CURRENT_VERSION;
    }

    public static String getDefaultBaseUrl() {
        return DEFAULT_BASE_URL;
    }

    public static Level getLoggingLevel() {
        return loggingLevel;
    }

    public static void setLoggingLevel(Level loggingLevel) {
        MercadoPagoConfig.loggingLevel = loggingLevel;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public static void setMetricsScope(String metricsScopeValue) {
        metricsScope = metricsScopeValue;
    }

    public static String getMetricsScope() {
        return metricsScope;
    }

    public static void setMaxConnections(int maxConnectionsValue) {
        maxConnections = maxConnectionsValue;
    }

    public static int getMaxConnections() {
        return maxConnections;
    }

    public static void setConnectionTimeout(int connectionTimeoutValue) {
        connectionTimeout = connectionTimeoutValue;
    }

    public static int getConnectionTimeout() {
        return connectionTimeout;
    }

    public static void setConnectionRequestTimeout(int connectionRequestTimeoutValue) {
        connectionRequestTimeout = connectionRequestTimeoutValue;
    }

    public static int getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }

    public static void setSocketTimeout(int socketTimeoutValue) {
        socketTimeout = socketTimeoutValue;
    }

    public static int getSocketTimeout() {
        return socketTimeout;
    }

    public static void setAccessToken(String accessTokenValue) {
        accessToken = accessTokenValue;
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public static void setCorporationId(String corporationIdValue) {
        corporationId = corporationIdValue;
    }
    public static String getCorporationId() {
        return corporationId;
    }

    public static void setIntegratorId(String integratorIdValue) {
        integratorId = integratorIdValue;
    }

    public static String getIntegratorId() {
        return integratorId;
    }

    public static void setPlatformId(String platformIdValue) {
        platformId = platformIdValue;
    }

    public static String getPlatformId() {
        return platformId;
    }

    public static void setRetryHandler(HttpRequestRetryHandler retryHandlerValue) {
        retryHandler = retryHandlerValue;
    }

    public static HttpRequestRetryHandler getRetryHandler() {
        return retryHandler;
    }

    public static void clearConfiguration() {

    }

    private static Object getJavaVersion(String property) {
        return null;
    }
}
