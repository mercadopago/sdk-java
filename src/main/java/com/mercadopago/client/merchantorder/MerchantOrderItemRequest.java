package com.mercadopago.client.merchantorder;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

/** Item information. */
@Getter
@Builder
public class MerchantOrderItemRequest {
  /** Item code. */
  private final String id;

  /** Item name. */
  private final String title;

  /** Item description. */
  private final String description;

  /** Image URL. */
  private final String pictureUrl;

  /** Category of the item. */
  private final String categoryId;

  /** Item's quantity. */
  private final int quantity;

  /** Unit price. */
  private final BigDecimal unitPrice;

  /** Currency ID. ISO_4217 code. */
  private final String currencyId;
}
