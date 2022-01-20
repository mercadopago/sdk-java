package com.mercadopago.resources.payment;

import java.math.BigDecimal;
import lombok.Getter;

/** PaymentTax class. */
@Getter
public class PaymentTax {
  /** Tax type. */
  private String type;

  /** Tax value. */
  private BigDecimal value;
}
