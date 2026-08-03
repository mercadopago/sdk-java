package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/**
 * Request object containing additional transaction details for a payment.
 * Used to specify the financial institution involved in bank-transfer
 * or other institution-dependent payment methods.
 */
@Getter
@Builder
public class PaymentTransactionDetailsRequest {
  /** Identifier of the external financial institution processing the payment. */
  private final String financialInstitution;
}
