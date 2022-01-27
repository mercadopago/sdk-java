package com.mercadopago.resources.merchantorder;

import java.math.BigDecimal;
import lombok.Getter;

/** Item information. */
@Getter
public class MerchantOrderItem {
  /** Item code. */
  private String id;

  /** Item name. */
  private String title;

  /** Item description. */
  private String description;

  /** Image URL. */
  private String pictureUrl;

  /** Category of the item. */
  private String categoryId;

  /** Item's quantity. */
  private int quantity;

  /** Unit price. */
  private BigDecimal unitPrice;

  /** Currency ID. ISO_4217 code. */
  private String currencyId;
}
