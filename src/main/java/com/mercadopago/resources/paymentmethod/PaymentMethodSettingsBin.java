package com.mercadopago.resources.paymentmethod;

import lombok.Getter;

/** Bin settings. */
@Getter
public class PaymentMethodSettingsBin {
  /** Regular expression representing the accepted bins. */
  private String pattern;

  /** Regular expression representing the excluded bins. */
  private String exclusionPattern;

  /** Regular expression representing bins allowed to pay with more than one installment. */
  private String installmentsPattern;
}
