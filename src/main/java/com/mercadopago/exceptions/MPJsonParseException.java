package com.mercadopago.exceptions;

/**
 * Exception thrown when JSON serialization or deserialization fails within the SDK.
 *
 * <p>This exception is raised by {@link com.mercadopago.serialization.Serializer} when a
 * JSON string cannot be parsed into the expected resource type, or when an object cannot
 * be converted to its JSON representation. Common causes include malformed JSON, unexpected
 * field types, or missing required properties.
 *
 * @see MPException
 * @see com.mercadopago.serialization.Serializer
 */
public class MPJsonParseException extends MPException {
  /**
   * Constructs a new JSON parse exception with the specified detail message.
   *
   * @param message a human-readable description of the parsing error
   */
  public MPJsonParseException(String message) {
    super(message);
  }

  /**
   * Constructs a new JSON parse exception with the specified detail message and underlying cause.
   *
   * @param message   a human-readable description of the parsing error
   * @param throwable the underlying throwable (e.g., a Gson or IO exception) that caused the failure
   */
  public MPJsonParseException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
