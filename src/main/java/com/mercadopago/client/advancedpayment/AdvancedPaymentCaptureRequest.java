package com.mercadopago.client.advancedpayment;

import lombok.Builder;
import lombok.Getter;

/** Request to capture an advanced payment (sets {@code capture} to {@code true}). */
@Getter
@Builder
public class AdvancedPaymentCaptureRequest {
  private final boolean capture = true;
}
