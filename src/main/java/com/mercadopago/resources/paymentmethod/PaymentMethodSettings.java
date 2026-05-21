package com.mercadopago.resources.paymentmethod;

import lombok.Getter;

/**
 * Resource that groups the validation and configuration settings for a MercadoPago payment method.
 *
 * <p>Combines BIN pattern rules, card number length and validation settings, and security
 * code requirements into a single settings object. A payment method may have multiple
 * settings entries for different card ranges.
 *
 * @see PaymentMethod#getSettings()
 */
@Getter
public class PaymentMethodSettings {
  /** BIN (Bank Identification Number) pattern and exclusion rules for card acceptance. */
  private PaymentMethodSettingsBin bin;

  /** Card number length and checksum validation settings. */
  private PaymentMethodSettingsCardNumber cardNumber;

  /** Security code (CVV/CVC) mode, length, and card location settings. */
  private PaymentMethodSettingsSecurityCode securityCode;
}
