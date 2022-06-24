package com.mercadopago.client.point;

import lombok.Builder;
import lombok.Getter;

/** Payment intent additional info. */
@Getter
@Builder
public class PointPaymentIntentAdditionalInfoRequest {
  /**
   * An alphanumeric value can be an identifier in your application. It will be returned in the
   * Webhook notification.
   */
  private final String externalReference;

  /** A boolean value that determines if you want to print the ticket on the device. */
  private final Boolean printOnTerminal;
}
