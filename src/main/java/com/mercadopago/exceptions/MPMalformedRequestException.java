package com.mercadopago.exceptions;

/** MPMalformedRequestException class. */
public class MPMalformedRequestException extends MPException {
  /**
   * MPMalformedRequestException constructor.
   *
   * @param message message
   */
  public MPMalformedRequestException(String message) {
    super(message);
  }

  /**
   * MPMalformedRequestException constructor.
   *
   * @param cause cause
   */
  public MPMalformedRequestException(Throwable cause) {
    super(cause);
  }
}
