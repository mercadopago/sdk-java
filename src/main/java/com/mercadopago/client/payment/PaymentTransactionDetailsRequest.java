package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/** PaymentTransactionDetailsRequest class. */
@Getter
@Builder
public class PaymentTransactionDetailsRequest {
  /** External financial institution identifier. */
  private final String financialInstitution;
}
