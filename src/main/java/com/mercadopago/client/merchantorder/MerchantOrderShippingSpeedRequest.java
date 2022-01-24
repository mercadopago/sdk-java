package com.mercadopago.client.merchantorder;

import lombok.Builder;
import lombok.Getter;

/** Shipping time information. */
@Getter
@Builder
public class MerchantOrderShippingSpeedRequest {
  /** Handling time. */
  private final long handling;

  /** Shipping time. */
  private final long shipping;
}
