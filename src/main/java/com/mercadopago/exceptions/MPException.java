package com.mercadopago.exceptions;

import lombok.Getter;

/**
 * Base checked exception for SDK-side errors in the MercadoPago Java SDK.
 *
 * <p>This exception represents failures that occur on the client side before or after
 * an API call, such as network errors, serialization failures, or invalid request
 * construction. It is <b>not</b> used for API-level error responses (HTTP 4xx/5xx);
 * those are represented by {@link MPApiException}.
 *
 * <p>Concrete subclasses provide more specific error categories:
 * <ul>
 *   <li>{@link MPJsonParseException} &ndash; JSON serialization/deserialization failures</li>
 *   <li>{@link MPMalformedRequestException} &ndash; invalid request construction errors</li>
 * </ul>
 *
 * @see MPApiException
 * @see MPJsonParseException
 * @see MPMalformedRequestException
 */
@Getter
public class MPException extends Exception {

  /**
   * Constructs a new SDK exception with the specified detail message.
   *
   * @param message a human-readable description of the error
   */
  public MPException(String message) {
    super(message);
  }

  /**
   * Constructs a new SDK exception with the specified detail message and underlying cause.
   *
   * @param message a human-readable description of the error
   * @param cause   the underlying throwable that triggered this exception
   */
  public MPException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs a new SDK exception wrapping an underlying cause.
   *
   * @param cause the underlying throwable that triggered this exception
   */
  public MPException(Throwable cause) {
    super(cause);
  }
}
