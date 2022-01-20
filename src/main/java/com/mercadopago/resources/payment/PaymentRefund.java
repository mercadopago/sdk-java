package com.mercadopago.resources.payment;

import com.mercadopago.resources.common.Source;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;

/** PaymentRefund class. */
@Getter
public class PaymentRefund {
  /** Refund id. */
  private Long id;

  /** ID of the refunded payment. */
  private Long paymentId;

  /** Amount refunded. */
  private BigDecimal amount;

  /** Refund status. */
  private String status;

  /** Refund mode. */
  private String refundMode;

  /** Date of creation. */
  private Date dateCreated;

  /** Unique sequence number. */
  private String uniqueSequenceNumber;

  /** Source of the refund. */
  private Source source;
}
