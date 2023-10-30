package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentInvoicePeriodRequest {
  /** Period. */
  private int period;
  /** Type. */
  private String type;
}
