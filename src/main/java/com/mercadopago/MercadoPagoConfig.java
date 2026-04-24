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

/**
 * Global configuration class for the MercadoPago Java SDK.
 *
 * <p>This class holds all SDK-wide settings such as the OAuth access token, HTTP timeouts,
 * connection pool limits, proxy configuration, logging behavior, and tracking identifiers.
 * All fields are static and thread-safe (declared {@code volatile} or synchronized), so a
 * single configuration applies across the entire application.
 *
 * <p>Per-request overrides (e.g., a different access token or timeout for one call) can be
 * specified via {@link com.mercadopago.core.MPRequestOptions}; any field left at its
 * zero/null default in that object will fall back to the values defined here.
 *
 * <p><b>Usage example:</b>
 * <pre>{@code
 * MercadoPagoConfig.setAccessToken("APP_USR-...");
 * MercadoPagoConfig.setConnectionTimeout(30000);
 * }</pre>
 *
 * @see com.mercadopago.core.MPRequestOptions
 * @see com.mercadopago.net.MPDefaultHttpClient
 */
public class MercadoPagoConfig {

  /** Current version of the MercadoPago Java SDK (used in tracking headers). */
  public static final String CURRENT_VERSION = "2.9.2";

  /** Internal MercadoPago product identifier sent in every API request for analytics. */
  public static final String PRODUCT_ID = "BC32A7VTRPP001U8NHJ0";

  /**
   * Tracking header value sent with every API request. Encodes the Java runtime version
   * and SDK version so the MercadoPago platform can identify the client environment.
   */
  public static final String TRACKING_ID = String.format(
      "platform:%s,type:SDK%s,so;",
      MercadoPagoConfig.getJavaVersion(), MercadoPagoConfig.CURRENT_VERSION);

  /** Base URL for all MercadoPago REST API calls. */
  public static final String BASE_URL = "https://api.mercadopago.com";

  /** Default maximum number of concurrent HTTP connections in the connection pool. Value: 10. */
  private static final int DEFAULT_MAX_CONNECTIONS = 10;

  /** Default timeout in milliseconds for establishing an HTTP connection. Value: 20 000 ms. */
  private static final int DEFAULT_CONNECTION_TIMEOUT_MS = 20000;

  /** Default timeout in milliseconds for obtaining a connection from the pool. Value: 20 000 ms. */
  private static final int DEFAULT_CONNECTION_REQUEST_TIMEOUT_MS = 20000;

  /** Default socket (read) timeout in milliseconds for waiting for response data. Value: 20 000 ms. */
  private static final int DEFAULT_SOCKET_TIMEOUT_MS = 20000;

  /** Default scope label used when reporting SDK metrics. Value: {@code "prod"}. */
  private static final String DEFAULT_METRICS_SCOPE = "prod";

  /** Default logging level for the SDK logger. Value: {@link Level#OFF} (logging disabled). */
  private static final Level DEFAULT_LOGGING_LEVEL = Level.OFF;

  /** OAuth access token used to authenticate API requests when no per-request token is provided. */
  @Getter
  @Setter
  private static volatile String accessToken;

  /** Platform identifier header sent with API requests for tracking and analytics. */
  @Getter
  @Setter
  private static volatile String platformId;

  /** Corporation identifier header sent with API requests for multi-entity tracking. */
  @Getter
  @Setter
  private static volatile String corporationId;

  /** Integrator identifier header sent with API requests to attribute traffic to a specific integrator. */
  @Getter
  @Setter
  private static volatile String integratorId;

  /**
   * Custom {@link StreamHandler} for SDK log output. When {@code null}, a default
   * {@link ConsoleHandler} is used instead.
   *
   * @see #getStreamHandler()
   */
  @Getter
  @Setter
  private static volatile StreamHandler loggingHandler;

  /** Scope label used when reporting SDK metrics. Defaults to {@code "prod"}. */
  @Getter
  @Setter
  private static volatile String metricsScope = DEFAULT_METRICS_SCOPE;

  /**
   * Java {@link Level} controlling the verbosity of SDK log output. Defaults to
   * {@link Level#OFF} (no logging).
   */
  @Getter
  @Setter
  private static volatile Level loggingLevel = DEFAULT_LOGGING_LEVEL;

  /** Maximum number of concurrent HTTP connections maintained in the connection pool. Defaults to 10. */
  @Getter
  @Setter
  private static volatile int maxConnections = DEFAULT_MAX_CONNECTIONS;

  /** Timeout in milliseconds for establishing a new HTTP connection. Defaults to 20 000 ms. */
  @Getter
  @Setter
  private static volatile int connectionTimeout = DEFAULT_CONNECTION_TIMEOUT_MS;

  /** Timeout in milliseconds for obtaining a connection from the pool. Defaults to 20 000 ms. */
  @Getter
  @Setter
  private static volatile int connectionRequestTimeout = DEFAULT_CONNECTION_REQUEST_TIMEOUT_MS;

  /** Timeout in milliseconds for waiting for response data on an open socket. Defaults to 20 000 ms. */
  @Getter
  @Setter
  private static volatile int socketTimeout = DEFAULT_SOCKET_TIMEOUT_MS;

  /**
   * HTTP client implementation used for all API calls. Lazily initialized to
   * {@link MPDefaultHttpClient} on first access via {@link #getHttpClient()}.
   */
  @Setter
  private static volatile MPHttpClient httpClient;

  /**
   * HTTP proxy host through which all API requests are routed. When {@code null},
   * requests are made directly without a proxy.
   */
  @Getter(onMethod_ = { @Synchronized })
  @Setter(onMethod_ = { @Synchronized })
  private static HttpHost proxy;

  /**
   * Custom retry handler for HTTP requests. When set, the underlying Apache HTTP client
   * will delegate retry decisions to this handler instead of using its default strategy.
   */
  @Getter
  @Setter
  private static HttpRequestRetryHandler retryHandler;

  /**
   * Returns the HTTP client used for API communication, creating a default
   * {@link MPDefaultHttpClient} instance on first invocation (lazy initialization).
   *
   * <p>The returned client is a singleton shared across all SDK calls. To replace it
   * with a custom implementation, use {@link #setHttpClient(MPHttpClient)} before making
   * any API requests.
   *
   * @return the current {@link MPHttpClient} instance, never {@code null}
   * @see MPDefaultHttpClient
   */
  public static synchronized MPHttpClient getHttpClient() {
    if (Objects.isNull(httpClient)) {
      httpClient = new MPDefaultHttpClient();
    }
    return httpClient;
  }

  /**
   * Returns the Java runtime version formatted as {@code "major|full"}.
   *
   * <p>For example, on Java 11.0.12+7, this method returns {@code "11|11.0.12+7"}.
   * The value is used in the {@link #TRACKING_ID} header sent with every API request.
   *
   * @return a string in the format {@code "majorVersion|fullVersion"}, or {@code null}
   *         if the {@code java.runtime.version} system property is unavailable
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
   * Returns the active {@link StreamHandler} for SDK logging. If a custom handler has been
   * set via {@link #setLoggingHandler(StreamHandler)}, it is returned; otherwise a new
   * {@link ConsoleHandler} is created as a fallback.
   *
   * @return the configured {@link StreamHandler}, or a default {@link ConsoleHandler}
   */
  public static StreamHandler getStreamHandler() {
    return Objects.nonNull(loggingHandler) ? loggingHandler : new ConsoleHandler();
  }
}
