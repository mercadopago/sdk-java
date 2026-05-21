package com.mercadopago.client.merchantorder;

import lombok.Builder;
import lombok.Getter;

/**
 * Request DTO representing the shipping speed breakdown for a Merchant Order shipping option.
 * Separates the total delivery time into handling (preparation) and transit (shipping) durations.
 *
 * @see <a href="https://www.mercadopago.com.br/developers/en/reference/merchant_orders">Merchant Orders API Reference</a>
 */
@Getter
@Builder
public class MerchantOrderShippingSpeedRequest {
  /** Handling time in hours required to prepare the shipment before dispatch. */
  private final Long handling;

  /** Transit time in hours from dispatch to delivery at the destination. */
  private final Long shipping;
}
