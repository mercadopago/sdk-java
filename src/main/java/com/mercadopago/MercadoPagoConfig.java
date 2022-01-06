package com.mercadopago;

import com.mercadopago.net.MPHttpClient;
import com.mercadopago.net.MPDefaultHttpClient;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.StreamHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.Synchronized;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpRequestRetryHandler;

public class MercadoPagoConfig {
  public static final String DEFAULT_BASE_URL = "https://api.mercadopago.com";

  private static final int DEFAULT_MAX_CONNECTIONS = 10;

  private static final int DEFAULT_CONNECTION_TIMEOUT_MS = 20000;

  private static final int DEFAULT_CONNECTION_REQUEST_TIMEOUT_MS = 20000;

  private static final int DEFAULT_SOCKET_TIMEOUT_MS = 20000;

  private static final String DEFAULT_METRICS_SCOPE = "prod";

  private static final Level DEFAULT_LOGGING_LEVEL = Level.OFF;

  @Getter
  private static final String baseUrl = DEFAULT_BASE_URL;

  @Getter
  @Setter
  private static volatile String accessToken = null;

  @Getter
  @Setter
  private static volatile String platformId = null;

  @Getter
  @Setter
  private static volatile String corporationId = null;

  @Getter
  @Setter
  private static volatile String integratorId = null;

  @Getter
  @Setter
  private static volatile String metricsScope = DEFAULT_METRICS_SCOPE;

  @Getter
  @Setter
  private static volatile StreamHandler loggingHandler = null;

  @Getter
  @Setter
  private static volatile Level loggingLevel = DEFAULT_LOGGING_LEVEL;

  @Getter
  @Setter
  private static volatile int maxConnections = DEFAULT_MAX_CONNECTIONS;

  @Getter
  @Setter
  private static volatile int connectionTimeout = DEFAULT_CONNECTION_TIMEOUT_MS;

  @Getter
  @Setter
  private static volatile int connectionRequestTimeout = DEFAULT_CONNECTION_REQUEST_TIMEOUT_MS;

  @Getter
  @Setter
  private static volatile int socketTimeout = DEFAULT_SOCKET_TIMEOUT_MS;

  @Setter
  private static volatile MPHttpClient httpClient = null;

  @Getter(onMethod_ = {@Synchronized})
  @Setter(onMethod_ = {@Synchronized})
  private static HttpHost proxy;

  @Getter
  @Setter
  private static HttpRequestRetryHandler retryHandler;

  public synchronized static MPHttpClient getHttpClient() {
    if (Objects.isNull(httpClient)) {
      httpClient = new MPDefaultHttpClient();
    }
    return httpClient;
  }

}
