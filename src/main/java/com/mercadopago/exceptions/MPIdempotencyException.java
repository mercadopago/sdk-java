package com.mercadopago.exceptions;

import com.mercadopago.net.MPResponse;

/**
 * Exception thrown when the MercadoPago API returns HTTP 409 Conflict.
 *
 * <p>Covers both idempotency-key conflicts and state-machine conflicts.
 */
public class MPIdempotencyException extends MPApiException {

  /**
   * MPIdempotencyException constructor.
   *
   * @param message message
   * @param response response
   */
  public MPIdempotencyException(String message, MPResponse response) {
    super(message, response);
  }

  /**
   * MPIdempotencyException constructor.
   *
   * @param message message
   * @param cause cause
   * @param response response
   */
  public MPIdempotencyException(String message, Throwable cause, MPResponse response) {
    super(message, cause, response);
  }
}
