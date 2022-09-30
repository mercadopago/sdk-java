package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/** PaymentMethodRequest class. */
@Getter
@Builder
public class PaymentMethodRequest {

  /** Data. */
  private final PaymentDataRequest data;
}
