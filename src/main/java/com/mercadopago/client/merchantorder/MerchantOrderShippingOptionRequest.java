package com.mercadopago.client.merchantorder;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

/**
 * Request DTO representing a shipping option for a Merchant Order. Defines the delivery method,
 * cost, estimated delivery window, and transit speed for shipping the order items.
 *
 * @see <a href="https://www.mercadopago.com.br/developers/en/reference/merchant_orders">Merchant Orders API Reference</a>
 */
@Getter
@Builder
public class MerchantOrderShippingOptionRequest {
  /** Unique identifier of the shipping option. */
  private final Long id;

  /** Net shipping cost charged to the buyer. */
  private final BigDecimal cost;

  /** ISO 4217 currency code for the shipping cost (e.g., "BRL", "ARS"). */
  private final String currencyId;

  /** Estimated delivery date and time window for this shipping option. */
  private final MerchantOrderShippingEstimatedDeliveryRequest estimatedDelivery;

  /** Full list price of shipping before any discounts. */
  private final BigDecimal listCost;

  /** Display name of the shipping option (e.g., "Standard", "Express"). */
  private final String name;

  /** Identifier of the shipping method or carrier service. */
  private final Long shippingMethodId;

  /** Handling and transit speed information for this shipping option. */
  private final MerchantOrderShippingSpeedRequest speed;
}
