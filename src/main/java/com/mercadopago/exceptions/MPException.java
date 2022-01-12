package com.mercadopago.exceptions;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/** MPException Class. */
@Getter
public class MPException extends Exception {

  /**
   * MPException constructor.
   *
   * @param message message
   */
  public MPException(String message) {
    super(message);
  }

  /**
   * MPException constructor.
   *
   * @param message message
   */
  public MPException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * MPException class.
   *
   * @param cause cause
   */
  public MPException(Throwable cause) {
    super(cause);
  }
}
