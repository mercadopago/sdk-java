package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Data;

/** PaymentTransactionDetailsRequest class. */
@Data
@Builder
public class PaymentTransactionDetailsRequest {
  private String financialInstitution;
}
