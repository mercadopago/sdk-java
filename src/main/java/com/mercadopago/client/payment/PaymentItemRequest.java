package com.mercadopago.client.payment;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Getter;

/** PaymentItemRequest class. */
@Getter
@Builder
public class PaymentItemRequest {
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

  /** Currency of the unit price. */
  private final String currencyId;

  /** Item's quantity. */
  private final Integer quantity;

  /** Unit price. */
  private final BigDecimal unitPrice;

  /** Item information related to the category. */
  private final PaymentCategoryDescriptorRequest categoryDescriptor;

  /** True if you purchase the item with warranty, false if not. */
  private final boolean warranty;

  /** Event date. */
  private final OffsetDateTime eventDate;
}
