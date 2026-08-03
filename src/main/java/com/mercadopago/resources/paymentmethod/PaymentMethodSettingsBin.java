package com.mercadopago.resources.paymentmethod;

import lombok.Getter;

/**
 * Resource that defines BIN (Bank Identification Number) validation rules for a payment method.
 *
 * <p>Uses regular expression patterns to determine which card BINs are accepted, excluded,
 * or eligible for installment payments. These rules help identify the card issuer and
 * control which cards can be used with a given payment method.
 *
 * @see PaymentMethodSettings#getBin()
 */
@Getter
public class PaymentMethodSettingsBin {
  /** Regular expression pattern matching the accepted BIN ranges for this payment method. */
  private String pattern;

  /** Regular expression pattern matching BIN ranges that are explicitly excluded. */
  private String exclusionPattern;

  /** Regular expression pattern matching BIN ranges eligible for multi-installment payments. */
  private String installmentsPattern;
}
