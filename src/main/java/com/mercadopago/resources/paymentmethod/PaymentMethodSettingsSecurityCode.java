package com.mercadopago.resources.paymentmethod;

import lombok.Getter;

/** Security code settings. */
@Getter
public class PaymentMethodSettingsSecurityCode {
  /** Whether the security code is mandatory or not. */
  private String mode;

  /** Security code length. */
  private int length;

  /** Whether the security code is located in the back or in the front of the card. */
  private String cardLocation;
}
