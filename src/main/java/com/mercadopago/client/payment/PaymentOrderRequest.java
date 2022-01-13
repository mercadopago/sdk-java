package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Data;

/** PaymentOrderRequest class. */
@Data
@Builder
public class PaymentOrderRequest {
  public Long id;

  public String type;
}
