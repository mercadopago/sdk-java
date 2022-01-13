package com.mercadopago.client.payment;

import java.math.BigDecimal;
import lombok.Builder;

/** PaymentCaptureRequest class. */
@Builder
public class PaymentCaptureRequest {
  private final boolean capture = true;

  private final BigDecimal transactionAmount;
}
