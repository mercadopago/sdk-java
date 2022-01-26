package com.mercadopago.resources.paymentmethod;

import lombok.Getter;

/** Payment method settings. */
@Getter
public class PaymentMethodSettings {
  /** Bin settings. */
  private PaymentMethodSettingsBin bin;

  /** Card number settings. */
  private PaymentMethodSettingsCardNumber cardNumber;

  /** Security code settings. */
  private PaymentMethodSettingsSecurityCode securityCode;
}
