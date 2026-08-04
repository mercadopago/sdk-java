package com.mercadopago.exceptions;

import com.mercadopago.net.MPResponse;

/** Exception thrown when the MercadoPago API returns HTTP 400 Bad Request. */
public class MPBadRequestException extends MPApiException {

  /**
   * MPBadRequestException constructor.
   *
   * @param message message
   * @param response response
   */
  public MPBadRequestException(String message, MPResponse response) {
    super(message, response);
  }

  /**
   * MPBadRequestException constructor.
   *
   * @param message message
   * @param cause cause
   * @param response response
   */
  public MPBadRequestException(String message, Throwable cause, MPResponse response) {
    super(message, cause, response);
  }
}
