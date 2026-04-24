package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/**
 * Request object representing the sequence position of a subscription payment.
 * Tracks which payment in the subscription series this is and the total
 * number of planned payments.
 */
@Getter
@Builder
public class PaymentSubscriptionSequenceRequest {
  /** Current payment number in the subscription sequence (e.g. 3 of 12). */
  private int number;
  /** Total number of payments planned in the subscription. */
  private int total;
}
