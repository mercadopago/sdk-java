package com.mercadopago.client.payment;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

/**
 * Request object used to capture a previously authorized (reserved) payment.
 * The capture flag is always set to {@code true}. An optional transaction
 * amount can be specified to perform a partial capture.
 */
@Getter
@Builder
public class PaymentCaptureRequest {
  /** Capture flag, always set to {@code true} to confirm the capture. */
  private final boolean capture = true;

  /** Amount to capture; if {@code null}, the full authorized amount is captured. */
  private final BigDecimal transactionAmount;
}
