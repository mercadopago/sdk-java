package com.mercadopago.exceptions;

/**
 * Mercado Pago SDK
 * Mercado Pago MPConf Exception Class
 */
public class MPConfException extends MPException {

  public MPConfException(String message) {
    super(message);
  }

  public MPConfException(Throwable cause) {
    super(cause);
  }
}
