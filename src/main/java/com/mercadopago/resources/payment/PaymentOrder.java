package com.mercadopago.resources.payment;

import lombok.Getter;

/** PaymentOrder class. */
@Getter
public class PaymentOrder {
  /** Id of the associated purchase order. */
  private Long id;

  /** Order type. */
  private String type;
}
