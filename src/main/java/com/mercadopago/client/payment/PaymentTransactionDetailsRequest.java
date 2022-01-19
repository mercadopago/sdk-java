package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/** PaymentTransactionDetailsRequest class. */
@Getter
@Builder
public class PaymentTransactionDetailsRequest {
  private final String financialInstitution;
}
