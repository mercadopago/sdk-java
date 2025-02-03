package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

// API version: b950ae02-4f49-4686-9ad3-7929b21b6495

/** OrderSubscriptionSequenceRequest class. */
@Getter
@Builder
public class OrderSubscriptionSequenceRequest {

  /** Number. */
  private Integer number;

  /** Total. */
  private Integer total;

}
