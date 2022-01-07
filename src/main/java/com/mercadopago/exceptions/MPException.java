package com.mercadopago.exceptions;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/** MPException Class. */
@Getter
public class MPException extends Exception {

  private String requestId;

  private Integer statusCode;

  /**
   * MPException constructor.
   *
   * @param message message
   */
  public MPException(String message) {
    this(message, null, null);
  }

  /**
   * MPException constructor.
   *
   * @param message message
   * @param requestId requestId
   * @param statusCode statusCode
   */
  public MPException(String message, String requestId, Integer statusCode) {
    super(message, null);
    this.requestId = requestId;
    this.statusCode = statusCode;
  }

  /**
   * MPException constructor.
   *
   * @param message message
   * @param requestId requestId
   * @param statusCode statusCode
   * @param cause cause
   */
  public MPException(String message, String requestId, Integer statusCode, Throwable cause) {
    super(message, cause);
    this.requestId = requestId;
    this.statusCode = statusCode;
  }

  /**
   * MPException class.
   *
   * @param cause cause
   */
  public MPException(Throwable cause) {
    super(cause);
  }

  @Override
  public String toString() {
    String reqIdStr = "";
    if (StringUtils.isNotEmpty(getRequestId())) {
      reqIdStr = "; request-id: " + getRequestId();
    }
    String statCodeStr = "";
    if (getStatusCode() != null) {
      statCodeStr = "; status_code: " + getStatusCode();
    }
    return super.toString() + reqIdStr + statCodeStr;
  }
}
