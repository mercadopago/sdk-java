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

  /** Long item description. */
  private final String description;

  /** Image URL. */
  private final String pictureUrl;

  /** Category of the item. */
  private final String categoryId;

  /** Item's quantity. */
  private final Integer quantity;

  /** Unit price. */
  private final BigDecimal unitPrice;

  /** Currency ID. ISO_4217 code. */
  private final String currencyId;
}
