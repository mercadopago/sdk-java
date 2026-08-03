package com.mercadopago.resources.payment;

import lombok.Getter;

/**
 * Resource that holds a reference to a related MercadoPago payment.
 *
 * <p>Used in recurring or linked payment contexts to point back to the original or
 * previous payment in a sequence.
 *
 * @see PaymentTransactionData#getPaymentReference()
 */
@Getter
public class PaymentPaymentReference {
  /** Unique identifier of the referenced payment. */
  private String id;
}
