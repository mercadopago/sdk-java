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

  /** Long item description. */
  private String description;

  /** Image URL. */
  private String pictureUrl;

  /** Category of the item. */
  private String categoryId;

  /** Item's quantity. */
  private Integer quantity;

  /** Unit price. */
  private BigDecimal unitPrice;
}
