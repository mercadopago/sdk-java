package com.mercadopago.client.point;

import lombok.Builder;
import lombok.Getter;

/**
 * Additional information for a Point payment intent, including external references and printing
 * options.
 *
 * @see com.mercadopago.client.point.PointPaymentIntentRequest
 */
@Getter
@Builder
public class PointPaymentIntentAdditionalInfoRequest {
  /** Alphanumeric identifier from your application, returned in the Webhook notification. */
  private final String externalReference;

  /** Whether to print the ticket on the Point device. */
  private final Boolean printOnTerminal;

  /** Alphanumeric invoice or ticket number, printed on the device ticket. */
  private final String ticketNumber;
}
