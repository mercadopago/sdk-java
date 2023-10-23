package com.mercadopago.client.payment;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class PaymentInvoicePeriodRequest {
  private int period;
  private String type;
}
