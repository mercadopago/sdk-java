package com.mercadopago.exceptions;

import com.mercadopago.net.MPResponse;
import lombok.Getter;

/**
 * Mercado Pago SDK
 * Mercado Pago MPRest Exception Class
 */
public class MPRestException extends MPException {

  public MPRestException(String message) {
    super(message);
  }

  public MPRestException(Throwable cause) {
    super(cause);
  }
}
