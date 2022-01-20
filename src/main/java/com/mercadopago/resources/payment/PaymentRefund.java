package com.mercadopago.resources.payment;

import com.mercadopago.resources.common.Source;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;

/** PaymentRefund class. */
@Getter
public class PaymentRefund {
  private Long id;

  private Long paymentId;

  private BigDecimal amount;

  private String status;

  private String refundMode;

  private Date dateCreated;

  private String uniqueSequenceNumber;

  private Source source;
}
