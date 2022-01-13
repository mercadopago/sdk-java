package com.mercadopago.client.payment;

import com.mercadopago.resources.payment.PaymentStatus;
import lombok.Data;

/** PaymentCancelRequest class. */
@Data
public class PaymentCancelRequest {
  private final String status = PaymentStatus.CANCELLED;
}
