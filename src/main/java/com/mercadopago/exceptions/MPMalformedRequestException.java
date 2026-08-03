package com.mercadopago.exceptions;

/**
 * Exception thrown when an API request cannot be constructed due to invalid or
 * incomplete input.
 *
 * <p>This exception is raised <b>before</b> any network call is made, indicating that
 * the request object is structurally invalid (e.g., missing required fields, malformed
 * URLs, or encoding failures). It signals a programming error on the caller's side
 * rather than an API-level or network-level failure.
 *
 * @see MPException
 * @see MPApiException
 */
public class MPMalformedRequestException extends MPException {
  /**
   * Constructs a new malformed request exception with the specified detail message.
   *
   * @param message a human-readable description of the request construction error
   */
  public MPMalformedRequestException(String message) {
    super(message);
  }

  /**
   * Constructs a new malformed request exception wrapping an underlying cause.
   *
   * @param cause the underlying throwable that prevented request construction
   */
  public MPMalformedRequestException(Throwable cause) {
    super(cause);
  }
}
