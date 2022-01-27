package com.mercadopago.resources.paymentmethod;

import lombok.Getter;

/** Card number settings. */
@Getter
public class PaymentMethodSettingsCardNumber {
  /** Card number length. */
  private Integer length;

  /** Whether the card number can be validated using a checksum algorithm (usually Luhn). */
  private String validation;
}
