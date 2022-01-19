package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/** PaymentOrderRequest class. */
@Getter
@Builder
public class PaymentOrderRequest {
  private final Long id;

  private final String type;
}
