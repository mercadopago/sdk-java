package com.mercadopago.resources.payment;

import lombok.Getter;

/** PaymentData class. */
@Getter
public class PaymentData {

  /** PaymentRules. */
  private PaymentRules rules;

  /** Reference Id. */
  private String referenceId;

  /** External Reference Id. */
  private String externalReferenceId;

  /** External Resource Url. */
  private String externalResourceUrl;
}
