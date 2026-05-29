package com.mercadopago.client.disbursementrefund;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

/**
 * Request to create a disbursement refund.
 *
 * @see com.mercadopago.client.disbursementrefund.DisbursementRefundClient
 */
@Getter
@Builder
public class DisbursementRefundCreateRequest {

  /** Amount to refund. When {@code null}, the full disbursement amount is refunded. */
  private final BigDecimal amount;
}
