package com.mercadopago.resources.merchantorder;

import lombok.Getter;

/** Shipping time information. */
@Getter
public class MerchantOrderShippingSpeed {
  /** Handling time. */
  private Long handling;

  /** Shipping time. */
  private Long shipping;
}
