package com.mercadopago.resources.disbursementrefund;

import com.mercadopago.net.MPResource;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Getter;

/**
 * Resource representing a refund on a disbursement within an advanced payment.
 *
 * @see com.mercadopago.client.disbursementrefund.DisbursementRefundClient
 */
@Getter
public class DisbursementRefund extends MPResource {

  /** Unique identifier of this disbursement refund. */
  private Long id;

  /** Identifier of the parent advanced payment. */
  private Long advancedPaymentId;

  /** Identifier of the disbursement that was refunded. */
  private Long disbursementId;

  /** Amount refunded. */
  private BigDecimal amount;

  /** Current status of the refund. */
  private String status;

  /** Timestamp when the refund was created. */
  private OffsetDateTime dateCreated;
}
