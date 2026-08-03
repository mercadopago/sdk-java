package com.mercadopago.resources.paymentmethod;

import lombok.Getter;

/**
 * Resource that defines security code (CVV/CVC) settings for a MercadoPago payment method.
 *
 * <p>Specifies whether the security code is mandatory, its expected length, and where
 * it is located on the physical card (front or back).
 *
 * @see PaymentMethodSettings#getSecurityCode()
 */
@Getter
public class PaymentMethodSettingsSecurityCode {
  /** Whether the security code is mandatory or optional (e.g. mandatory, optional). */
  private String mode;

  /** Expected number of digits in the security code (typically 3 or 4). */
  private int length;

  /** Physical location of the security code on the card (e.g. back, front). */
  private String cardLocation;
}
