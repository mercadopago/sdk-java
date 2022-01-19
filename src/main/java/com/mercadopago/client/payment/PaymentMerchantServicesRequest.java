package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/** PaymentMerchantServicesRequest class. */
@Getter
@Builder
public class PaymentMerchantServicesRequest {
  private final boolean fraudScoring;

  private final boolean fraudManualReview;
}
