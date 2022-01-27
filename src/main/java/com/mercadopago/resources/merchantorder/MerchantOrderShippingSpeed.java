package com.mercadopago.resources.merchantorder;

import lombok.Getter;

/** Shipping time information. */
@Getter
public class MerchantOrderShippingSpeed {
  /** Handling time. */
  private long handling;

  /** Shipping time. */
  private long shipping;
}
