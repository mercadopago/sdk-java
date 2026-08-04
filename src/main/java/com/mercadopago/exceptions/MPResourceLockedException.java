package com.mercadopago.exceptions;

import com.mercadopago.net.MPResponse;

/**
 * Exception thrown when the MercadoPago API returns HTTP 423 Locked.
 *
 * <p>Indicates the idempotency key is temporarily locked. This error is retryable.
 */
public class MPResourceLockedException extends MPApiException {

  /**
   * MPResourceLockedException constructor.
   *
   * @param message message
   * @param response response
   */
  public MPResourceLockedException(String message, MPResponse response) {
    super(message, response);
  }

  /**
   * MPResourceLockedException constructor.
   *
   * @param message message
   * @param cause cause
   * @param response response
   */
  public MPResourceLockedException(String message, Throwable cause, MPResponse response) {
    super(message, cause, response);
  }
}
