package com.mercadopago.exceptions;

import com.mercadopago.net.MPResponse;

/** Exception thrown when the MercadoPago API returns HTTP 422 Unprocessable Entity (business rule violations). */
public class MPValidationException extends MPApiException {

  /**
   * MPValidationException constructor.
   *
   * @param message message
   * @param response response
   */
  public MPValidationException(String message, MPResponse response) {
    super(message, response);
  }

  /**
   * MPValidationException constructor.
   *
   * @param message message
   * @param cause cause
   * @param response response
   */
  public MPValidationException(String message, Throwable cause, MPResponse response) {
    super(message, cause, response);
  }
}
