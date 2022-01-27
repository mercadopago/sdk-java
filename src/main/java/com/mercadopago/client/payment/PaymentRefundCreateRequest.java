package com.mercadopago.client.payment;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

/** Refund creation request data. */
@Getter
@Builder
public class PaymentRefundCreateRequest {
  /** Amount to be refunded. */
  private final BigDecimal amount;
}
