package com.mercadopago.client.preference;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

/** Purchased item. */
@Getter
@Builder
public class PreferenceItemRequest {
  /** Item code. */
  private final String id;

  /** Item name. */
  private final String title;

  /** Type of the item. */
  private final String type;

  /** Long item description. */
  private final String description;

  /** Image URL. */
  private final String pictureUrl;

  /** Category of the item. */
  private final String categoryId;

  /** Currency ID. Code ISO_4217. */
  private final String currencyId;

  /** Item's quantity. */
  private final Integer quantity;

  /** Unit price. */
  private final BigDecimal unitPrice;

  /** Category descriptor*/
  private final PreferenceCategoryDescriptorRequest categoryDescriptor;

  /** True if you purchase the item with warranty, false if not. */
  private final boolean warranty;
}

