package com.mercadopago.resources.payment;

import lombok.Getter;

/**
 * Resource that holds a credential-on-file reference identifier for a MercadoPago payment.
 *
 * <p>Used in CREDENTIAL_ON_FILE flows to link the current payment to a stored
 * payment credential.
 *
 * @see PaymentTransactionData#getReference()
 */
@Getter
public class PaymentReference {
  /** Unique identifier of the stored credential reference. */
  private String id;
}
