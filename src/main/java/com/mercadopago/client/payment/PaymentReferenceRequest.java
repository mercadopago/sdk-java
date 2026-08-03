package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/**
 * Request object carrying a credential-on-file reference identifier.
 * Used within transaction data to link the current payment to a stored credential.
 */
@Getter
@Builder
public class PaymentReferenceRequest {
  /** Identifier of the stored credential reference. */
  private String id;
}
