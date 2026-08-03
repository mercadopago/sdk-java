package com.mercadopago.exceptions;

import com.mercadopago.net.MPResponse;

/** Exception thrown when the MercadoPago API returns an HTTP 5xx server error. */
public class MPServerException extends MPApiException {

  /**
   * MPServerException constructor.
   *
   * @param message message
   * @param response response
   */
  public MPServerException(String message, MPResponse response) {
    super(message, response);
  }

  /**
   * MPServerException constructor.
   *
   * @param message message
   * @param cause cause
   * @param response response
   */
  public MPServerException(String message, Throwable cause, MPResponse response) {
    super(message, cause, response);
  }
}
