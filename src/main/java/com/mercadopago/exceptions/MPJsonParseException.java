package com.mercadopago.exceptions;

/** MPJsonParseException class. */
public class MPJsonParseException extends MPException {
  /**
   * MPJsonParseException constructor.
   *
   * @param message message
   */
  public MPJsonParseException(String message) {
    super(message);
  }

  /**
   * MPJsonParseException constructor.
   *
   * @param message message
   * @param throwable throwable
   */
  public MPJsonParseException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
