package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

// API version: 1ff4822a-2dfd-4393-800e-a562edb3fe32

/**
 * Request object representing the sequence position of a payment within a subscription.
 * Indicates which payment number this is out of the total number of payments in the
 * subscription plan.
 */
@Getter
@Builder
public class OrderSubscriptionSequenceRequest {

  /** Current payment number in the subscription sequence (e.g. 3 for the third payment). */
  private Integer number;

  /** Total number of payments in the subscription plan. */
  private Integer total;

}
