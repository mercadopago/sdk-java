package com.mercadopago.resources.payment;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Getter;

/** PaymentDiscount class. */
@Getter
@Builder
public class PaymentDiscount {

  /** Discount type. */
  private String type;

  /** Discount value. */
  private BigDecimal value;

  /** Discount Limit Date. */
  private OffsetDateTime limitDate;
}
