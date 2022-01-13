package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Data;

/** PaymentMerchantServicesRequest class. */
@Data
@Builder
public class PaymentMerchantServicesRequest {
  private boolean fraudScoring;

  private boolean fraudManualReview;
}
