package com.mercadopago.resources.point;

import lombok.Getter;

/**
 * Resource containing additional metadata for a Point payment intent.
 *
 * <p>Holds supplementary information that enriches the payment intent, including an external
 * reference for integration with external systems, ticket printing preferences, and a ticket
 * number for invoice tracking.
 *
 * @see PointPaymentIntent
 */
@Getter
public class PointPaymentIntentAdditionalInfo {
  /**
   * An alphanumeric value can be an identifier in your application. It will be returned in the
   * Webhook notification.
   */
  private String externalReference;

  /** A boolean value that determines if you want to print the ticket on the device. */
  private Boolean printOnTerminal;

  /**
   * An alphanumeric value to identify the invoice or ticket number. It will be printed on the
   * device ticket.
   */
  private String ticketNumber;
}
