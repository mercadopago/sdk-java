package com.mercadopago.resources.payment;

import com.mercadopago.resources.common.Source;
import com.mercadopago.net.MPResource;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Getter;

/** PaymentRefund class. */
@Getter
public class PaymentRefund extends MPResource {
  /** Refund id. */
  private Long id;

  /** ID of the refunded payment. */
  private Long paymentId;

  /** Amount refunded. */
  private BigDecimal amount;

  /** Adjustment amount. */
  private BigDecimal adjustmentAmount;

  /** Refund status. */
  private String status;

  /** Refund mode. */
  private String refundMode;

  /** Date of creation. */
  private OffsetDateTime dateCreated;

  /** Refund reason. */
  private String reason;

  /** Unique sequence number. */
  private String uniqueSequenceNumber;

  /** Source of the refund. */
  private Source source;
}
