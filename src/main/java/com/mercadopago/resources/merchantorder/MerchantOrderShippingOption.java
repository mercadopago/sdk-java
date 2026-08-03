package com.mercadopago.resources.merchantorder;

import java.math.BigDecimal;
import lombok.Getter;

/**
 * Represents a shipping option available for a merchant order shipment in the MercadoPago API.
 *
 * <p>Contains cost, currency, estimated delivery, shipping method, and speed information for a
 * specific shipping option that can be selected for order fulfillment.
 *
 * @see MerchantOrderShipment
 */
@Getter
public class MerchantOrderShippingOption {
  /** Unique identifier of the shipping option. */
  private Long id;

  /** Net cost absorbed by the receiver (buyer). */
  private BigDecimal cost;

  /** Currency identifier in ISO 4217 format (e.g., BRL, ARS, USD). */
  private String currencyId;

  /** Estimated delivery date and time window. */
  private MerchantOrderShippingEstimatedDelivery estimatedDelivery;

  /** Listed (original) cost of the shipping before discounts. */
  private BigDecimal listCost;

  /** Display name of the shipping option. */
  private String name;

  /** Identifier of the shipping method. */
  private Long shippingMethodId;

  /** Speed details including handling and shipping times. */
  private MerchantOrderShippingSpeed speed;
}
