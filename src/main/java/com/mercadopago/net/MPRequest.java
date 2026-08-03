package com.mercadopago.net;

import com.google.gson.JsonObject;
import com.mercadopago.core.MPRequestOptions;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

/**
 * Immutable value object representing an HTTP request to be sent to the MercadoPago API.
 *
 * <p>Instances are created via the Lombok-generated builder or through the
 * {@link #buildRequest(String, HttpMethod, JsonObject, Map, MPRequestOptions)} factory method,
 * which merges per-request options (custom headers, access token, timeouts) from
 * {@link MPRequestOptions} into the request.
 *
 * <p>When a timeout value is {@code 0} (the default), the corresponding global timeout from
 * {@link com.mercadopago.MercadoPagoConfig} is used at execution time.
 *
 * @see MPRequestOptions
 * @see MPHttpClient#send(MPRequest)
 * @see MPDefaultHttpClient
 */
@Getter
@Builder
public class MPRequest {

  /** The fully-qualified URI (or relative path) for the API endpoint. */
  private final String uri;

  /** The HTTP method to use for this request. */
  private final HttpMethod method;

  /** Custom HTTP headers to include in this request, keyed by header name. */
  private final Map<String, String> headers;

  /**
   * The JSON request body. May be {@code null} for methods that do not carry a payload
   * (e.g., GET, DELETE).
   */
  private final JsonObject payload;

  /** Query parameters to append to the URI, keyed by parameter name. */
  private final Map<String, Object> queryParams;

  /**
   * The OAuth access token for this specific request. When set, it overrides the global token
   * from {@link com.mercadopago.MercadoPagoConfig#getAccessToken()}.
   */
  private final String accessToken;

  /**
   * Connection timeout in milliseconds for this request. A value of {@code 0} means the global
   * default from {@link com.mercadopago.MercadoPagoConfig#getConnectionTimeout()} is used.
   */
  private final int connectionTimeout;

  /**
   * Timeout in milliseconds for obtaining a connection from the connection pool. A value of
   * {@code 0} means the global default from
   * {@link com.mercadopago.MercadoPagoConfig#getConnectionRequestTimeout()} is used.
   */
  private final int connectionRequestTimeout;

  /**
   * Socket (read) timeout in milliseconds for this request. A value of {@code 0} means the
   * global default from {@link com.mercadopago.MercadoPagoConfig#getSocketTimeout()} is used.
   */
  private final int socketTimeout;

  /**
   * Factory method that builds an {@link MPRequest} from the given path, method, payload, query
   * parameters, and optional per-request options.
   *
   * <p>When {@code requestOptions} is not {@code null}, its custom headers, access token, and
   * timeout values are applied to the resulting request. When {@code requestOptions} is
   * {@code null}, only the path, method, and payload are set.
   *
   * @param path           the API endpoint path (relative or absolute URL)
   * @param method         the {@link HttpMethod} to use
   * @param payload        the JSON body for the request, or {@code null} if none
   * @param queryParams    a map of query parameter names to values, or {@code null} if none
   * @param requestOptions per-request options including custom headers, access token, and
   *                       timeouts; may be {@code null} for defaults
   * @return a fully constructed {@link MPRequest} ready to be sent via
   *         {@link MPHttpClient#send(MPRequest)}
   */
  public static MPRequest buildRequest(
      String path,
      HttpMethod method,
      JsonObject payload,
      Map<String, Object> queryParams,
      MPRequestOptions requestOptions) {
    MPRequest mpRequest;

    if (Objects.nonNull(requestOptions)) {
      mpRequest =
          MPRequest.builder()
              .uri(path)
              .method(method)
              .headers(requestOptions.getCustomHeaders())
              .payload(payload)
              .queryParams(queryParams)
              .accessToken(requestOptions.getAccessToken())
              .connectionRequestTimeout(requestOptions.getConnectionRequestTimeout())
              .connectionTimeout(requestOptions.getConnectionTimeout())
              .socketTimeout(requestOptions.getSocketTimeout())
              .build();
    } else {
      mpRequest = MPRequest.builder().uri(path).method(method).payload(payload).build();
    }

    return mpRequest;
  }

  /**
   * Generates a new, random idempotency key based on a version-4 UUID.
   *
   * <p>The returned value is suitable for use as the {@link Headers#IDEMPOTENCY_KEY} header to
   * ensure that a POST request can be safely retried without creating duplicate resources.
   *
   * @return a random UUID string to be used as an idempotency key
   * @see Headers#IDEMPOTENCY_KEY
   */
  public String createIdempotencyKey() {
    return UUID.randomUUID().toString();
  }
}
