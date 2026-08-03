package com.mercadopago.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import lombok.Builder;
import lombok.Data;

/**
 * Per-request configuration overrides for MercadoPago API calls.
 *
 * <p>Instances of this class allow callers to override global settings defined in
 * {@link com.mercadopago.MercadoPagoConfig} on a request-by-request basis. Any field
 * left at its default value ({@code null} for objects, {@code 0} for primitives) will
 * fall back to the corresponding global configuration value at execution time.
 *
 * <p>Use the Lombok-generated builder for fluent construction:
 * <pre>{@code
 * MPRequestOptions options = MPRequestOptions.builder()
 *     .accessToken("APP_USR-...")
 *     .connectionTimeout(5000)
 *     .customHeaders(Map.of("X-Idempotency-Key", uuid))
 *     .build();
 * }</pre>
 *
 * @see com.mercadopago.MercadoPagoConfig
 */
@Data
@Builder
public class MPRequestOptions {

  /** Default maximum number of retry attempts for retryable status codes. */
  public static final int DEFAULT_MAX_RETRIES = 3;

  /** Default socket and connection timeout in milliseconds (60 seconds). */
  public static final long DEFAULT_TIMEOUT_MS = 60_000L;

  /** Default maximum delay between retries in milliseconds (30 seconds). */
  public static final long DEFAULT_MAX_DELAY_MS = 30_000L;

  /** Default set of HTTP status codes that trigger a retry. */
  public static final List<Integer> DEFAULT_RETRY_ON =
      Collections.unmodifiableList(Arrays.asList(429, 500, 502, 503, 504));

  /** OAuth access token for this specific request, overriding the global token when set. */
  private String accessToken;

  /** HTTP connection timeout in milliseconds for this request. A value of {@code 0} uses the global default. */
  private int connectionTimeout;

  /** Timeout in milliseconds for obtaining a connection from the pool. A value of {@code 0} uses the global default. */
  private int connectionRequestTimeout;

  /** Socket (read) timeout in milliseconds for this request. A value of {@code 0} uses the global default. */
  private int socketTimeout;

  /** Additional HTTP headers to include in this request (e.g., idempotency keys, custom tracing headers). */
  private Map<String, String> customHeaders;

  /**
   * Maximum number of retry attempts. {@code null} means use {@link #DEFAULT_MAX_RETRIES}.
   * Must be &ge; 0.
   */
  private Integer maxRetries;

  /**
   * HTTP status codes that should trigger a retry. {@code null} means use {@link #DEFAULT_RETRY_ON}.
   */
  private List<Integer> retryOn;

  /**
   * Initial delay in milliseconds before the first retry.
   * {@code null} means no additional backoff delay. Must be &ge; 0.
   */
  private Long initialDelayMs;

  /**
   * Maximum delay in milliseconds between retries. {@code null} means use {@link #DEFAULT_MAX_DELAY_MS}.
   * Must be &ge; 0.
   */
  private Long maxDelayMs;

  /**
   * Whether to add random jitter to the retry delay using {@link java.security.SecureRandom}.
   * {@code null} or {@code false} means no jitter.
   */
  private Boolean jitter;

  /**
   * Optional callback invoked before each retry attempt.
   * First arg is attempt number (1-based), second is the triggering exception.
   */
  private BiConsumer<Integer, Exception> onRetry;

  /**
   * Sets maxRetries with non-negative validation.
   *
   * @param maxRetries must be &ge; 0
   * @throws IllegalArgumentException if negative
   */
  public void setMaxRetries(Integer maxRetries) {
    if (maxRetries != null && maxRetries < 0) {
      throw new IllegalArgumentException("maxRetries must be >= 0, got: " + maxRetries);
    }
    this.maxRetries = maxRetries;
  }

  /**
   * Sets initialDelayMs with non-negative validation.
   *
   * @param initialDelayMs must be &ge; 0
   * @throws IllegalArgumentException if negative
   */
  public void setInitialDelayMs(Long initialDelayMs) {
    if (initialDelayMs != null && initialDelayMs < 0) {
      throw new IllegalArgumentException("initialDelayMs must be >= 0, got: " + initialDelayMs);
    }
    this.initialDelayMs = initialDelayMs;
  }

  /**
   * Sets maxDelayMs with non-negative validation.
   *
   * @param maxDelayMs must be &ge; 0
   * @throws IllegalArgumentException if negative
   */
  public void setMaxDelayMs(Long maxDelayMs) {
    if (maxDelayMs != null && maxDelayMs < 0) {
      throw new IllegalArgumentException("maxDelayMs must be >= 0, got: " + maxDelayMs);
    }
    this.maxDelayMs = maxDelayMs;
  }

  /**
   * Sets retryOn with HTTP status code validation (100–599).
   *
   * @param retryOn list of valid HTTP status codes; may be {@code null}
   * @throws IllegalArgumentException if any code is outside 100–599
   */
  public void setRetryOn(java.util.List<Integer> retryOn) {
    if (retryOn != null) {
      for (Integer code : retryOn) {
        if (code == null || code < 100 || code > 599) {
          throw new IllegalArgumentException(
              "retryOn contains invalid HTTP status code: " + code);
        }
      }
    }
    this.retryOn = retryOn;
  }

  /**
   * Creates a default {@link MPRequestOptions} with all fields at their zero/null defaults,
   * meaning every setting will fall back to the global {@link com.mercadopago.MercadoPagoConfig}.
   *
   * @return a new {@link MPRequestOptions} instance with no overrides
   */
  public static MPRequestOptions createDefault() {
    return MPRequestOptions.builder().build();
  }
}
