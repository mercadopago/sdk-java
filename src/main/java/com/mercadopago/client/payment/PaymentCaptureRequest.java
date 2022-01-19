package com.mercadopago.client.payment;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

/** PaymentCaptureRequest class. */
@Getter
@Builder
public class PaymentCaptureRequest {
  private final boolean capture = true;

  private final BigDecimal transactionAmount;
}
