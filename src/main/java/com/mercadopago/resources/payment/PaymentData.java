package com.mercadopago.resources.payment;

import lombok.Getter;

/** PaymentData class. */
@Getter
public class PaymentData {

  /** PaymentRules. */
  private PaymentRules rules;

  /** External Reference Id. */
  private String externalReferenceId;
}
