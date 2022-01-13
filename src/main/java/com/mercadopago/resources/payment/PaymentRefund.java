package com.mercadopago.resources.payment;

import com.mercadopago.resources.common.Source;
import java.util.Date;
import lombok.Data;

/** PaymentRefund class. */
@Data
public class PaymentRefund {
  private Long id;

  private Long paymentId;

  private Double amount;

  private String status;

  private String refundMode;

  private Date dateCreated;

  private String uniqueSequenceNumber;

  private Source source;
}
