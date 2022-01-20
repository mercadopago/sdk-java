package com.mercadopago.client.payment;

import static com.mercadopago.resources.payment.PaymentStatus.CANCELLED;

import lombok.Builder;
import lombok.Getter;

/** PaymentCancelRequest class. */
@Getter
@Builder
public class PaymentCancelRequest {
  /** Status cancelled. */
  private final String status = CANCELLED;
}
