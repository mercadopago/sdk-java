package com.mercadopago.exceptions;

import com.mercadopago.net.MPResponse;

/**
 * Exception thrown when the MercadoPago API returns HTTP 424 Failed Dependency.
 *
 * <p>Indicates an internal dependency failure. This error is retryable like 5xx errors.
 */
public class MPDependencyException extends MPApiException {

  /**
   * MPDependencyException constructor.
   *
   * @param message message
   * @param response response
   */
  public MPDependencyException(String message, MPResponse response) {
    super(message, response);
  }

  /**
   * MPDependencyException constructor.
   *
   * @param message message
   * @param cause cause
   * @param response response
   */
  public MPDependencyException(String message, Throwable cause, MPResponse response) {
    super(message, cause, response);
  }
}
