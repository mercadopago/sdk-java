package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

// API version: 1ff4822a-2dfd-4393-800e-a562edb3fe32

/** OrderSubscriptionSequenceRequest class. */
@Getter
@Builder
public class OrderSubscriptionSequenceRequest {

  /** Number. */
  private Integer number;

  /** Total. */
  private Integer total;

}
