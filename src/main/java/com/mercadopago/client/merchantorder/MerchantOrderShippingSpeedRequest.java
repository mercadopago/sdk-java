package com.mercadopago.client.merchantorder;

import lombok.Builder;
import lombok.Getter;

/** Shipping time information. */
@Getter
@Builder
public class MerchantOrderShippingSpeedRequest {
  /** Handling time. */
  private final Long handling;

  /** Shipping time. */
  private final Long shipping;
}
