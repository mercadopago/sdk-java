package com.mercadopago.exceptions;

import com.mercadopago.net.MPResponse;
import lombok.Getter;

/**
 * Exception thrown when the MercadoPago API returns HTTP 429 Too Many Requests.
 *
 * <p>Exposes the {@code retryAfter} value parsed from the {@code Retry-After} response header
 * (in seconds), or {@code null} if the header is absent.
 */
@Getter
public class MPRateLimitException extends MPApiException {

  /**
   * The number of seconds to wait before retrying, parsed from the {@code Retry-After} header.
   * {@code null} if the header was not present.
   */
  private final Long retryAfter;

  /**
   * MPRateLimitException constructor.
   *
   * @param message message
   * @param response response
   * @param retryAfter seconds to wait before retrying, or {@code null}
   */
  public MPRateLimitException(String message, MPResponse response, Long retryAfter) {
    super(message, response);
    this.retryAfter = retryAfter;
  }

  /**
   * MPRateLimitException constructor.
   *
   * @param message message
   * @param cause cause
   * @param response response
   * @param retryAfter seconds to wait before retrying, or {@code null}
   */
  public MPRateLimitException(String message, Throwable cause, MPResponse response, Long retryAfter) {
    super(message, cause, response);
    this.retryAfter = retryAfter;
  }

  /**
   * MPRateLimitException constructor without retryAfter (defaults to null).
   *
   * @param message message
   * @param response response
   */
  public MPRateLimitException(String message, MPResponse response) {
    this(message, response, null);
  }

  /**
   * MPRateLimitException constructor without retryAfter (defaults to null).
   *
   * @param message message
   * @param cause cause
   * @param response response
   */
  public MPRateLimitException(String message, Throwable cause, MPResponse response) {
    this(message, cause, response, null);
  }
}
