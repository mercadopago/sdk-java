package com.mercadopago.resources.paymentmethod;

import lombok.Getter;

/**
 * Resource that defines card number validation settings for a MercadoPago payment method.
 *
 * <p>Specifies the expected card number length and whether checksum validation (typically
 * the Luhn algorithm) should be applied to verify the card number.
 *
 * @see PaymentMethodSettings#getCardNumber()
 */
@Getter
public class PaymentMethodSettingsCardNumber {
  /** Expected number of digits in the card number. */
  private Integer length;

  /** Validation algorithm to apply (e.g. standard for Luhn checksum, or none). */
  private String validation;
}
