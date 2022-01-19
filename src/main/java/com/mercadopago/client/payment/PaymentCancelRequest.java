package com.mercadopago.client.payment;

import com.mercadopago.resources.payment.PaymentStatus;
import lombok.Getter;

/** PaymentCancelRequest class. */
@Getter
public class PaymentCancelRequest {
  private final String status = PaymentStatus.CANCELLED;
}
