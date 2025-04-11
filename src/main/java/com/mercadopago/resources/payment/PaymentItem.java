package com.mercadopago.resources.payment;

import java.math.BigDecimal;
import lombok.Getter;

/** PaymentItem class. */
@Getter
public class PaymentItem {
  /** Item code. */
  private String id;

  /** Item name. */
  private String title;

  /** Type of the item. */
  private String type;

  /** Long item description. */
  private String description;

  /** Image URL. */
  private String pictureUrl;

  /** Category of the item. */
  private String categoryId;

  /** Currency of the unit price. */
  private String currencyId;

  /** Item's quantity. */
  private Integer quantity;

  /** Unit price. */
  private BigDecimal unitPrice;

  /** True if the item has warranty, false if not. */
  private boolean warranty;
}
