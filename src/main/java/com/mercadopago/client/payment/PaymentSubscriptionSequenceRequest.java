package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentSubscriptionSequenceRequest {
  private int number;
  private int total;
}
