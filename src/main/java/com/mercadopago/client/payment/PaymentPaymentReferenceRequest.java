package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentPaymentReferenceRequest {
  /** Payment ID reference. */
  private String id;
}
