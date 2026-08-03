package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/**
 * Request object containing network transaction data for recurring payments.
 * Carries the card-brand network transaction identifier used to link
 * subsequent recurring charges to the original authorization.
 */
@Getter
@Builder
public class PaymentNetworkTransactionDataRequest {
  /** Network transaction identifier assigned by the card brand (Visa/Mastercard). */
  private final String networkTransactionId;
} 