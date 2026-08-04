package com.mercadopago.exceptions;

import com.mercadopago.net.MPResponse;

/** Exception thrown when the MercadoPago API returns HTTP 403 Forbidden. */
public class MPForbiddenException extends MPApiException {

  /**
   * MPForbiddenException constructor.
   *
   * @param message message
   * @param response response
   */
  public MPForbiddenException(String message, MPResponse response) {
    super(message, response);
  }

  /**
   * MPForbiddenException constructor.
   *
   * @param message message
   * @param cause cause
   * @param response response
   */
  public MPForbiddenException(String message, Throwable cause, MPResponse response) {
    super(message, cause, response);
  }
}
