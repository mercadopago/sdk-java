package com.mercadopago.resources.payment;

import java.math.BigDecimal;
import lombok.Getter;

/** PaymentTax class. */
@Getter
public class PaymentTax {
  private String type;

  private BigDecimal value;
}
