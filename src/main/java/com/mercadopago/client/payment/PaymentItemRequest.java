package com.mercadopago.client.payment;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Getter;

/**
 * Request object describing an individual item included in a payment.
 * Sent inside the additional-info section to provide detailed product data
 * that supports fraud-prevention analysis and improves the payer experience.
 */
@Getter
@Builder
public class PaymentItemRequest {
  /** Unique item code or SKU in the merchant's catalog. */
  private final String id;

  /** Display name of the item. */
  private final String title;

  /** Type classification of the item (e.g. "electronics", "tickets"). */
  private final String type;

  /** Detailed description of the item. */
  private final String description;

  /** URL of the item's image. */
  private final String pictureUrl;

  /** Category identifier for the item in the merchant's catalog. */
  private final String categoryId;

  /** ISO 4217 currency code for the unit price. */
  private final String currencyId;

  /** Quantity of this item being purchased. */
  private final Integer quantity;

  /** Unit price of the item. */
  private final BigDecimal unitPrice;

  /** Category-specific descriptor with details such as passenger or route information. */
  private final PaymentCategoryDescriptorRequest categoryDescriptor;

  /** Whether the item is purchased with warranty coverage. */
  private final boolean warranty;

  /** Date of the event associated with the item (e.g. concert, flight). */
  private final OffsetDateTime eventDate;
}
