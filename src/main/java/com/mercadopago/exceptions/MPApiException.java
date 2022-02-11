package com.mercadopago.exceptions;

import com.mercadopago.net.MPResponse;
import lombok.Getter;

/** MPApiException class. */
@Getter
public class MPApiException extends Exception {
  private final int statusCode;

  private final MPResponse apiResponse;

  /**
   * MPApiException constructor.
   *
   * @param message message
   * @param response response
   */
  public MPApiException(String message, MPResponse response) {
    this(message, null, response);
  }

  /**
   * MPApiException constructor.
   *
   * @param message message
   * @param cause cause
   * @param response response
   */
  public MPApiException(String message, Throwable cause, MPResponse response) {
    super(message, cause);
    this.apiResponse = response;
    this.statusCode = response.getStatusCode();
  }
}
