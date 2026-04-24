package com.mercadopago.resources.preference;

import java.math.BigDecimal;
import lombok.Getter;

/**
 * Resource representing an item included in a checkout preference.
 *
 * <p>Describes a product or service the buyer is purchasing, including its title, price, quantity,
 * and optional metadata such as category, image, and warranty information.
 *
 * @see Preference
 * @see PreferenceCategoryDescriptor
 */
@Getter
public class PreferenceItem {
  /** Unique item code or SKU identifier. */
  private String id;

  /** Display title or name of the item. */
  private String title;

  /** Type classification of the item. */
  private String type;

  /** Detailed description of the item. */
  private String description;

  /** URL of the item's representative image. */
  private String pictureUrl;

  /** MercadoPago category identifier for the item. */
  private String categoryId;

  /** Number of units of this item being purchased. */
  private int quantity;

  /** Price per unit in the specified currency. */
  private BigDecimal unitPrice;

  /** Whether the item includes a warranty. */
  private boolean warranty;

  /** ISO 4217 currency code for the item's price (e.g., ARS, BRL, USD). */
  private String currencyId;

  /** Category-specific descriptor with additional details (e.g., passenger and route for travel). */
  private PreferenceCategoryDescriptor categoryDescriptor;
}
