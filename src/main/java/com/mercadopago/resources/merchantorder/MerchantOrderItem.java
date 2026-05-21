package com.mercadopago.resources.merchantorder;

import java.math.BigDecimal;
import lombok.Getter;

/**
 * Represents an item within a merchant order in the MercadoPago API.
 *
 * <p>Contains product information such as title, description, image, category, quantity, and
 * unit price. A merchant order may contain one or more items.
 *
 * @see MerchantOrder
 */
@Getter
public class MerchantOrderItem {
  /** Unique identifier (code) of the item. */
  private String id;

  /** Title or name of the item. */
  private String title;

  /** Detailed description of the item. */
  private String description;

  /** URL of the item's image. */
  private String pictureUrl;

  /** Category identifier for the item. */
  private String categoryId;

  /** Quantity of this item in the order. */
  private int quantity;

  /** Unit price of the item. */
  private BigDecimal unitPrice;

  /** Currency identifier in ISO 4217 format (e.g., BRL, ARS, USD). */
  private String currencyId;
}
