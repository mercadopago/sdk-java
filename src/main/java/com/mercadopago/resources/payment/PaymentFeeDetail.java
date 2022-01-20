package com.mercadopago.resources.payment;

import java.math.BigDecimal;
import lombok.Getter;

/** PaymentFeeDetail class. */
@Getter
public class PaymentFeeDetail {
  /** Fee type. */
  private String type;

  /** Who absorbs the cost. */
  private String feePayer;

  /** Fee amount. */
  private BigDecimal amount;
}
