package com.mercadopago.resources.preference;

import java.math.BigDecimal;
import lombok.Getter;

/** Purchased item. */
@Getter
public class PreferenceItem {
  /** Item code. */
  private String id;

  /** Item name. */
  private String title;

  /** Tipo do item. */
  private String type;

  /** Long item description. */
  private String description;

  /** Image URL. */
  private String pictureUrl;

  /** Category of the item. */
  private String categoryId;

  /** Item's quantity. */
  private int quantity;

  /** Unit price. */
  private BigDecimal unitPrice;

  /** Verdadeiro se o item tiver garantia, falso se não. */
  private boolean warranty;

  /** Currency ID. ISO_4217 code. */
  private String currencyId;

  /** Category Descriptor */
  private PreferenceCategoryDescriptor categoryDescriptor;
}
