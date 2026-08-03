package com.mercadopago.client.payment;

import static com.mercadopago.resources.payment.PaymentStatus.CANCELLED;

import lombok.Builder;
import lombok.Getter;

/**
 * Request object used to cancel an existing payment.
 * Sets the payment status to {@code cancelled}. Only payments that have
 * not yet been approved or are in a pending/in-process state can be cancelled.
 */
@Getter
@Builder
public class PaymentCancelRequest {
  /** Payment status, automatically set to {@code cancelled}. */
  private final String status = CANCELLED;
}
