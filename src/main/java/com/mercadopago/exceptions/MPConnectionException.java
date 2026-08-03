package com.mercadopago.exceptions;

import com.mercadopago.net.MPResponse;

/**
 * Exception thrown when a network or timeout error occurs (non-HTTP exceptions).
 *
 * <p>Wraps low-level transport exceptions such as {@link java.io.IOException},
 * {@link javax.net.ssl.SSLPeerUnverifiedException}, and connection timeouts.
 */
public class MPConnectionException extends MPApiException {

  /**
   * MPConnectionException constructor.
   *
   * @param message message
   * @param response response
   */
  public MPConnectionException(String message, MPResponse response) {
    super(message, response);
  }

  /**
   * MPConnectionException constructor.
   *
   * @param message message
   * @param cause cause
   * @param response response
   */
  public MPConnectionException(String message, Throwable cause, MPResponse response) {
    super(message, cause, response);
  }
}
