package com.mercadopago.resources.payment;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

/** PaymentFeeRequest class. */
@Getter
@Builder
public class PaymentFee {

  /** Fee type. */
  private String type;

  /** Fee value. */
  private BigDecimal value;
}
