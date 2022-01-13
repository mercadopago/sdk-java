package com.mercadopago.resources.payment;

import lombok.Data;

/** PaymentTax class. */
@Data
public class PaymentTax {
  private String type;

  private Double value;
}
