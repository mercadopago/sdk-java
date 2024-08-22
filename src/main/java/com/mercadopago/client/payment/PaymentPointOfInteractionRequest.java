package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/** PaymentPointOfInteractionRequest class. */
@Getter
@Builder
public class PaymentPointOfInteractionRequest {
  /** Linked to information. */
  private final String linkedTo;

  /** Type. */
  private final String type;

  //** Sub Type */
  private final String subType;

  /**  Transaction data. */
  private final PaymentTransactionDataRequest transactionData;

}
