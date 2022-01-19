package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/** PaymentTaxRequest class. */
@Getter
@Builder
public class PaymentTaxRequest {
  private final String type;

  private final double value;
}
