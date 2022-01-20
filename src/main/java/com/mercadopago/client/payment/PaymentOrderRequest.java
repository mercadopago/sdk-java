package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/** PaymentOrderRequest class. */
@Getter
@Builder
public class PaymentOrderRequest {
  /** Id of the associated purchase order. */
  private final Long id;

  /** Order type. */
  private final String type;
}
