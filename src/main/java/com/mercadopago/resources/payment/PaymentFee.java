package com.mercadopago.resources.payment;

import java.math.BigDecimal;
import lombok.Getter;

/** PaymentFeeRequest class. */
@Getter
public class PaymentFee {

  /** Fee type. */
  private String type;

  /** Fee value. */
  private BigDecimal value;

  /** Amount Applied. */
  private BigDecimal amountApplied;
}
