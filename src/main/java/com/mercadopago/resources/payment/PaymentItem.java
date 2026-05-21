package com.mercadopago.resources.payment;

import java.math.BigDecimal;
import lombok.Getter;

/**
 * Resource that represents a single item being purchased in a MercadoPago payment.
 *
 * <p>Contains details about the product or service such as title, description, quantity,
 * unit price, and category. Item information is included in the additional info and helps
 * with fraud prevention and purchase tracking.
 *
 * @see PaymentAdditionalInfo#getItems()
 */
@Getter
public class PaymentItem {
  /** Unique item code or SKU provided by the merchant. */
  private String id;

  /** Display name of the item. */
  private String title;

  /** Type classification of the item. */
  private String type;

  /** Detailed description of the item. */
  private String description;

  /** URL of the item's image for display purposes. */
  private String pictureUrl;

  /** Category identifier that classifies the item (e.g. electronics, clothing). */
  private String categoryId;

  /** ISO 4217 currency code of the unit price. */
  private String currencyId;

  /** Number of units of this item being purchased. */
  private Integer quantity;

  /** Price per unit of the item. */
  private BigDecimal unitPrice;

  /** Whether the item includes a warranty ({@code true}) or not ({@code false}). */
  private boolean warranty;
}
