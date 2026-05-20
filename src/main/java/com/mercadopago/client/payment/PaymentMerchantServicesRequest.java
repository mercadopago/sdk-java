package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/**
 * Request object configuring the merchant's anti-fraud services for a payment.
 * Controls whether automated fraud scoring and/or manual fraud review are
 * enabled for the transaction.
 */
@Getter
@Builder
public class PaymentMerchantServicesRequest {
  /** Whether automated fraud-scoring analysis is enabled for this payment. */
  private final boolean fraudScoring;

  /** Whether manual fraud review by a specialist is enabled for this payment. */
  private final boolean fraudManualReview;
}
