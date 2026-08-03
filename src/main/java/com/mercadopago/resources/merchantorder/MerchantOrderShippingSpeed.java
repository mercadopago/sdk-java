package com.mercadopago.resources.merchantorder;

import lombok.Getter;

/**
 * Represents shipping speed details for a shipping option in the MercadoPago API.
 *
 * <p>Contains the handling time (preparation before dispatch) and shipping time (transit duration)
 * in hours, used to calculate total estimated delivery time.
 *
 * @see MerchantOrderShippingOption
 */
@Getter
public class MerchantOrderShippingSpeed {
  /** Handling time in hours before the package is dispatched. */
  private Long handling;

  /** Transit time in hours from dispatch to delivery. */
  private Long shipping;
}
