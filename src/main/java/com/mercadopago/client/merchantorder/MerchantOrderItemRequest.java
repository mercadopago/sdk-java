package com.mercadopago.client.merchantorder;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

/**
 * Request DTO representing an item within a Merchant Order. Each item describes a product or
 * service being sold, including its price, quantity, and descriptive information.
 *
 * @see <a href="https://www.mercadopago.com.br/developers/en/reference/merchant_orders">Merchant Orders API Reference</a>
 */
@Getter
@Builder
public class MerchantOrderItemRequest {
  /** Unique item code or SKU identifier. */
  private final String id;

  /** Display title or name of the item. */
  private final String title;

  /** Detailed description of the item. */
  private final String description;

  /** URL of the item's image for display purposes. */
  private final String pictureUrl;

  /** Category identifier for the item (e.g., "electronics", "clothing"). */
  private final String categoryId;

  /** Number of units of this item in the order. */
  private final int quantity;

  /** Price per unit of the item. */
  private final BigDecimal unitPrice;

  /** ISO 4217 currency code for the unit price (e.g., "BRL", "ARS", "USD"). */
  private final String currencyId;
}
