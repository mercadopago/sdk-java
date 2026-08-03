package com.mercadopago.exceptions;

import com.mercadopago.net.MPResponse;

/** Exception thrown when the MercadoPago API returns HTTP 401 Unauthorized. */
public class MPAuthenticationException extends MPApiException {

  /**
   * MPAuthenticationException constructor.
   *
   * @param message message
   * @param response response
   */
  public MPAuthenticationException(String message, MPResponse response) {
    super(message, response);
  }

  /**
   * MPAuthenticationException constructor.
   *
   * @param message message
   * @param cause cause
   * @param response response
   */
  public MPAuthenticationException(String message, Throwable cause, MPResponse response) {
    super(message, cause, response);
  }
}
