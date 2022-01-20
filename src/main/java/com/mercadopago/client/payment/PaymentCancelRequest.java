package com.mercadopago.client.payment;

import com.mercadopago.resources.payment.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

/** PaymentCancelRequest class. */
@Getter
@Builder
public class PaymentCancelRequest {
  private final String status = PaymentStatus.CANCELLED;
}
