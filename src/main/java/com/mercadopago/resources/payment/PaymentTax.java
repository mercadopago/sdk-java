package com.mercadopago.resources.payment;

import java.math.BigDecimal;
import lombok.Data;

/** PaymentTax class. */
@Data
public class PaymentTax {
  private String type;

  private BigDecimal value;
}
