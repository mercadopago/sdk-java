package com.mercadopago.client.preference;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Getter;

/**
 * Represents an item to be purchased within a checkout preference. Each item includes pricing,
 * quantity, and optional category and warranty details.
 *
 * @see com.mercadopago.client.preference.PreferenceRequest
 * @see com.mercadopago.client.preference.PreferenceCategoryDescriptorRequest
 */
@Getter
@Builder
public class PreferenceItemRequest {
  /** Unique item identifier or code in your system. */
  private final String id;

  /** Display name of the item. */
  private final String title;

  /** Type classification of the item. */
  private final String type;

  /** Detailed description of the item. */
  private final String description;

  /** URL of the item's image. */
  private final String pictureUrl;

  /** Category identifier for the item. */
  private final String categoryId;

  /** Currency identifier in ISO 4217 format (e.g., ARS, BRL). */
  private final String currencyId;

  /** Number of units of this item. */
  private final Integer quantity;

  /** Price per unit of the item. */
  private final BigDecimal unitPrice;

  /** Category-specific descriptor with additional item metadata. */
  private final PreferenceCategoryDescriptorRequest categoryDescriptor;

  /** Whether the item includes a warranty. */
  private final boolean warranty;

  /** Date of the event related to the item. */
  private final OffsetDateTime eventDate;
}

