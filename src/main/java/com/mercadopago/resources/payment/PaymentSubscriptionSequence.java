package com.mercadopago.resources.payment;

import lombok.Getter;

/**
 * Resource that represents the sequence position of a subscription-based MercadoPago payment.
 *
 * <p>Tracks the current installment number within the total number of scheduled payments
 * for a recurring subscription.
 *
 * @see PaymentTransactionData#getSubscriptionSequence()
 */
@Getter
public class PaymentSubscriptionSequence {
  /** Current sequence number of this payment within the subscription (e.g. 3 of 12). */
  private int number;

  /** Total number of payments expected in the subscription. */
  private int total;
}
