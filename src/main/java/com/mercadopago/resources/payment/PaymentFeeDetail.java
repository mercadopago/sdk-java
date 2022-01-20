package com.mercadopago.resources.payment;

import java.math.BigDecimal;
import lombok.Getter;

/** PaymentFeeDetail class. */
@Getter
public class PaymentFeeDetail {
  private String type;

  private String feePayer;

  private BigDecimal amount;
}
