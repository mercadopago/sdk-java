package com.mercadopago.exceptions;

import com.mercadopago.net.MPResponse;

/** Exception thrown when the MercadoPago API returns HTTP 402 Payment Required. */
public class MPPaymentException extends MPApiException {

  /**
   * MPPaymentException constructor.
   *
   * @param message message
   * @param response response
   */
  public MPPaymentException(String message, MPResponse response) {
    super(message, response);
  }

  /**
   * MPPaymentException constructor.
   *
   * @param message message
   * @param cause cause
   * @param response response
   */
  public MPPaymentException(String message, Throwable cause, MPResponse response) {
    super(message, cause, response);
  }
}
