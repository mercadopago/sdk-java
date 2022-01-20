package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/** PaymentMerchantServicesRequest class. */
@Getter
@Builder
public class PaymentMerchantServicesRequest {
  /** Fraud scoring. */
  private final boolean fraudScoring;

  /** Fraud manual review. */
  private final boolean fraudManualReview;
}
