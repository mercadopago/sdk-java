package com.mercadopago.client.payment;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;

/** PaymentItemRequest class. */
@Getter
@Builder
public class PaymentItemRequest {
  private final String id;

  private final String title;

  private final String description;

  private final String pictureUrl;

  private final String categoryId;

  private final Integer quantity;

  private final BigDecimal unitPrice;

  private final PaymentCategoryDescriptorRequest categoryDescriptor;

  private final boolean warranty;

  private final Date eventDate;
}
