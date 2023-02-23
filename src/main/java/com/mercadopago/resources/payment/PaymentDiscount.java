package com.mercadopago.resources.payment;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;

/** PaymentDiscount class. */
@Getter
public class PaymentDiscount {

  /** Discount type. */
  private String type;

  /** Discount value. */
  private BigDecimal value;

  /** Discount Limit Date. */
  private LocalDate limitDate;
}
