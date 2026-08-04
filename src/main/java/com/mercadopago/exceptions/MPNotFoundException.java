package com.mercadopago.exceptions;

import com.mercadopago.net.MPResponse;

/** Exception thrown when the MercadoPago API returns HTTP 404 Not Found. */
public class MPNotFoundException extends MPApiException {

  /**
   * MPNotFoundException constructor.
   *
   * @param message message
   * @param response response
   */
  public MPNotFoundException(String message, MPResponse response) {
    super(message, response);
  }

  /**
   * MPNotFoundException constructor.
   *
   * @param message message
   * @param cause cause
   * @param response response
   */
  public MPNotFoundException(String message, Throwable cause, MPResponse response) {
    super(message, cause, response);
  }
}
