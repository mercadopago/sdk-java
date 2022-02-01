package com.mercadopago.exceptions;

/** */
public class MPJsonParseException extends MPException {
  /** @param message */
  public MPJsonParseException(String message) {
    super(message);
  }

  /**
   * @param message
   * @param throwable
   */
  public MPJsonParseException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
