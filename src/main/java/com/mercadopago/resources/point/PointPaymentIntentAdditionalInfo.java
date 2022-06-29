package com.mercadopago.resources.point;

import lombok.Getter;

/** Payment intent additional info. */
@Getter
public class PointPaymentIntentAdditionalInfo {
  /**
   * An alphanumeric value can be an identifier in your application. It will be returned in the
   * Webhook notification.
   */
  private String externalReference;

  /** A boolean value that determines if you want to print the ticket on the device. */
  private Boolean printOnTerminal;
}
