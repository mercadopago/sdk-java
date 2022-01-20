package com.mercadopago.client.payment;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

/** PaymentCaptureRequest class. */
@Getter
@Builder
public class PaymentCaptureRequest {
  /** Capture true. */
  private final boolean capture = true;

  /** The amount to capture. */
  private final BigDecimal transactionAmount;
}
