package com.mercadopago.resources.payment;

import com.mercadopago.resources.common.Identification;
import lombok.Getter;

/** PaymentCardholder class. */
@Getter
public class PaymentCardholder {
  /** Cardholder Name. */
  private String name;

  /** Cardholder identification. */
  private Identification identification;
}
