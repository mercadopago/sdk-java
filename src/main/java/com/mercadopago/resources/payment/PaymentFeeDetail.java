package com.mercadopago.resources.payment;

import lombok.Data;

/** PaymentFeeDetail class. */
@Data
public class PaymentFeeDetail {
  private String type;

  private String feePayer;

  private Double amount;
}
