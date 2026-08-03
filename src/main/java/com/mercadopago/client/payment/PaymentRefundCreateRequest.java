package com.mercadopago.client.payment;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

/**
 * Request object used to create a refund for an existing payment.
 * Allows specifying a partial or full refund amount. When the amount is
 * {@code null}, a full refund of the original payment is performed.
 */
@Getter
@Builder
public class PaymentRefundCreateRequest {
  /** Amount to be refunded. If {@code null}, the full payment amount is refunded. */
  private final BigDecimal amount;
}
