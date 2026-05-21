package com.mercadopago.net;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Immutable value object representing an HTTP response returned by the MercadoPago API.
 *
 * <p>Instances are created by {@link MPDefaultHttpClient} after executing a request. The response
 * carries the HTTP status code, all response headers (with multi-value support), and the raw
 * response body as a string.
 *
 * <p>This object is also attached to {@link MPResource} subclasses so that callers can inspect
 * the underlying HTTP response metadata even after deserialization.
 *
 * @see MPHttpClient#send(MPRequest)
 * @see MPResource#getResponse()
 */
@Getter
@AllArgsConstructor
public class MPResponse {

  /**
   * The HTTP status code of the response (e.g., 200, 201, 400).
   *
   * @see HttpStatus
   */
  private final Integer statusCode;

  /**
   * A map of response header names to their values. Each header name may map to multiple values
   * because HTTP allows repeated headers with the same name.
   */
  private final Map<String, List<String>> headers;

  /**
   * The raw response body as a UTF-8 string. May be empty for responses with no content
   * (e.g., HTTP 204 No Content).
   */
  private final String content;
}
