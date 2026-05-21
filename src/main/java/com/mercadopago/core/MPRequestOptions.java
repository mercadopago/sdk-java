package com.mercadopago.core;

import java.util.Map;
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
   * Creates a default {@link MPRequestOptions} with all fields at their zero/null defaults,
   * meaning every setting will fall back to the global {@link com.mercadopago.MercadoPagoConfig}.
   *
   * @return a new {@link MPRequestOptions} instance with no overrides
   */
  public static MPRequestOptions createDefault() {
    return MPRequestOptions.builder().build();
  }
}
