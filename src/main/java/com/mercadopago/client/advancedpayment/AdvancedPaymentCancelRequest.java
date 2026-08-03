package com.mercadopago.client.advancedpayment;

import lombok.Getter;

/** Request to cancel an advanced payment (sets {@code status} to {@code "cancelled"}). */
@Getter
public class AdvancedPaymentCancelRequest {
  private final String status = "cancelled";
}
