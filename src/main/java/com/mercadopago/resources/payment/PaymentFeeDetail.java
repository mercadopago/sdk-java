package com.mercadopago.resources.payment;

import java.math.BigDecimal;
import lombok.Data;

/** PaymentFeeDetail class. */
@Data
public class PaymentFeeDetail {
  private String type;

  private String feePayer;

  private BigDecimal amount;
}
