package com.mercadopago.exceptions;

import com.mercadopago.net.MPResponse;
import lombok.Getter;

/**
 * Exception thrown when the MercadoPago API returns an error response (HTTP 4xx/5xx).
 *
 * <p>Unlike {@link MPException}, which covers client-side failures (network errors,
 * serialization issues), this exception specifically represents a successfully received
 * API response that indicates a business or validation error. It carries the HTTP status
 * code and the full {@link MPResponse} so callers can inspect headers, body, and status
 * for error-handling logic.
 *
 * <p><b>Usage example:</b>
 * <pre>{@code
 * try {
 *     Payment payment = client.create(request);
 * } catch (MPApiException e) {
 *     System.out.println("Status: " + e.getStatusCode());
 *     System.out.println("Response: " + e.getApiResponse().getContent());
 * }
 * }</pre>
 *
 * @see MPException
 * @see com.mercadopago.net.MPResponse
 */
@Getter
public class MPApiException extends Exception {
  /** HTTP status code returned by the MercadoPago API (e.g., 400, 401, 404, 500). */
  private final int statusCode;

  /** Full API response including headers, status code, and body content. */
  private final MPResponse apiResponse;

  /**
   * Constructs a new API exception with the specified message and response.
   *
   * @param message  a human-readable description of the API error
   * @param response the full {@link MPResponse} received from the API
   */
  public MPApiException(String message, MPResponse response) {
    this(message, null, response);
  }

  /**
   * Constructs a new API exception with the specified message, underlying cause, and response.
   *
   * @param message  a human-readable description of the API error
   * @param cause    the underlying throwable that triggered this exception, or {@code null}
   * @param response the full {@link MPResponse} received from the API
   */
  public MPApiException(String message, Throwable cause, MPResponse response) {
    super(message, cause);
    this.apiResponse = response;
    this.statusCode = response.getStatusCode();
  }
}
