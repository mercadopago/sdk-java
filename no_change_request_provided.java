package com.mercadopago;

/**
 * Placeholder class created when no specific change request was provided.
 *
 * <p>This class serves as a demonstration of the MercadoPago Java SDK structure
 * and coding conventions. In a real scenario, this would be replaced with actual
 * implementation based on specific requirements.
 *
 * <p><b>Usage example:</b>
 * <pre>{@code
 * NoChangeRequestProvided example = new NoChangeRequestProvided();
 * String message = example.getMessage();
 * System.out.println(message);
 * }</pre>
 *
 * @see MercadoPagoConfig
 */
public class NoChangeRequestProvided {

  /** Default message indicating no change request was provided. */
  private static final String DEFAULT_MESSAGE = 
      "No change request provided. Please specify the required changes.";

  /** Instance message field. */
  private final String message;

  /**
   * Default constructor. Initializes with the default message.
   */
  public NoChangeRequestProvided() {
    this.message = DEFAULT_MESSAGE;
  }

  /**
   * Constructor with custom message.
   *
   * @param message the custom message to use
   */
  public NoChangeRequestProvided(String message) {
    this.message = message != null ? message : DEFAULT_MESSAGE;
  }

  /**
   * Retrieves the current message.
   *
   * @return the message string
   */
  public String getMessage() {
    return message;
  }

  /**
   * Checks if this instance is using the default message.
   *
   * @return {@code true} if using the default message, {@code false} otherwise
   */
  public boolean isDefaultMessage() {
    return DEFAULT_MESSAGE.equals(message);
  }

  /**
   * Returns a string representation of this object.
   *
   * @return a string containing the class name and message
   */
  @Override
  public String toString() {
    return "NoChangeRequestProvided{message='" + message + "'}";
  }

  /**
   * Compares this object with another for equality.
   *
   * @param obj the object to compare with
   * @return {@code true} if the objects are equal, {@code false} otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    NoChangeRequestProvided that = (NoChangeRequestProvided) obj;
    return message.equals(that.message);
  }

  /**
   * Returns a hash code value for this object.
   *
   * @return the hash code
   */
  @Override
  public int hashCode() {
    return message.hashCode();
  }
}