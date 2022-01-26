package com.mercadopago.resources.paymentmethod;

import lombok.Getter;

/** Financial institution processing this payment method. */
@Getter
public class PaymentMethodFinancialInstitutions {
  /** External financial institution identifier (e.g.: company id for atm) */
  private Long id;

  /** Descriptive financial institution name. */
  private String description;
}
