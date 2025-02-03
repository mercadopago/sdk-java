package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

// API version: b950ae02-4f49-4686-9ad3-7929b21b6495

/** OrderStoredCredentialRequest class. */
@Getter
@Builder
public class OrderStoredCredentialRequest {

  /** Payment Initiator. */
  private String paymentInitiator;

  /** Reason. */
  private String reason;

  /** Store Payment Method. */
  private Boolean storePaymentMethod;

  /** First Payment. */
  private Boolean firstPayment;
}
