package com.mercadopago.resources.payment;

import lombok.Getter;

/**
 * Resource that represents the payment method details associated with a MercadoPago payment.
 *
 * <p>Contains the data structure that holds payment rules, reference identifiers, and
 * external resource URLs specific to the payment method used in the transaction.
 *
 * @see Payment
 * @see PaymentData
 */
@Getter
public class PaymentMethod {

  /** Data containing rules, references, and external resource URLs for this payment method. */
  private PaymentData data;
}
