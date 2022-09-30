package com.mercadopago.client.payment;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

/** PaymentFeeRequest class. */
@Getter
@Builder
public class PaymentFeeRequest {

  /** Fee type. */
  private String type;

  /** Fee value. */
  private BigDecimal value;
}
