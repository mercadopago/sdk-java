package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/**
 * Request object containing a reference to a previous payment in a subscription series.
 * Used within transaction data to link the current payment to an earlier authorization.
 */
@Getter
@Builder
public class PaymentPaymentReferenceRequest {
  /** Identifier of the referenced previous payment. */
  private String id;
}
