package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentSubscriptionSequenceRequest {
  /** Number. */
  private int number;
  /** Total. */
  private int total;
}
