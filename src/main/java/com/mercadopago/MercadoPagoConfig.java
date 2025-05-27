package com.mercadopago;

import com.mercadopago.net.MPDefaultHttpClient;
import com.mercadopago.net.MPHttpClient;
import java.util.Objects;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.StreamHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.Synchronized;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpRequestRetryHandler;

/** Mercado Pago configuration class. */
public class MercadoPagoConfig {

  public static final String CURRENT_VERSION = "2.5.0";

  public static final String PRODUCT_ID = "BC32A7VTRPP001U8NHJ0";

  public static final String TRACKING_ID = String.format(
      "platform:%s,type:SDK%s,so;",
      MercadoPagoConfig.getJavaVersion(), MercadoPagoConfig.CURRENT_VERSION);

  public static final String BASE_URL = "https://api.mercadopago.com";

  private static final int DEFAULT_MAX_CONNECTIONS = 10;

  private static final int DEFAULT_CONNECTION_TIMEOUT_MS = 20000;

  private static final int DEFAULT_CONNECTION_REQUEST_TIMEOUT_MS = 20000;

  private static final int DEFAULT_SOCKET_TIMEOUT_MS = 20000;

  private static final String DEFAULT_METRICS_SCOPE = "prod";

  private static final Level DEFAULT_LOGGING_LEVEL = Level.OFF;

  @Getter
  @Setter
  private static volatile String accessToken;

  @Getter
  @Setter
  private static volatile String platformId;

  @Getter
  @Setter
  private static volatile String corporationId;

  @Getter
  @Setter
  private static volatile String integratorId;

  @Getter
  @Setter
  private static volatile StreamHandler loggingHandler;

  @Getter
  @Setter
  private static volatile String metricsScope = DEFAULT_METRICS_SCOPE;

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
  private static volatile MPHttpClient httpClient;

  @Getter(onMethod_ = { @Synchronized })
  @Setter(onMethod_ = { @Synchronized })
  private static HttpHost proxy;

  @Getter
  @Setter
  private static HttpRequestRetryHandler retryHandler;

  /**
   * Verifies which http client use.
   *
   * @return MPHttpClient
   */
  public static synchronized MPHttpClient getHttpClient() {
    if (Objects.isNull(httpClient)) {
      httpClient = new MPDefaultHttpClient();
    }
    return httpClient;
  }

  /**
   * Method responsible for return Java version.
   *
   * @return java version
   */
  public static synchronized String getJavaVersion() {
    String version = System.getProperty("java.runtime.version");
    if (Objects.isNull(version)) {
      return null;
    }

    String major = version.replaceAll("^1\\.", "");
    int dotIndex = major.indexOf('.');
    if (dotIndex != -1) {
      major = major.substring(0, dotIndex);
    }

    return major + "|" + version;
  }

  /**
   * Method responsible for return StreamHandler.
   *
   * @return StreamHandler
   */
  public static StreamHandler getStreamHandler() {
    return Objects.nonNull(loggingHandler) ? loggingHandler : new ConsoleHandler();
  }
}
