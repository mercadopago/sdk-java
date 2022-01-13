package com.mercadopago.client.payment;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Builder;
import lombok.Data;

/** PaymentItemRequest class. */
@Data
@Builder
public class PaymentItemRequest {
  private String id;

  private String title;

  private String description;

  private String pictureUrl;

  private String categoryId;

  private Integer quantity;

  private BigDecimal unitPrice;

  private PaymentCategoryDescriptorRequest categoryDescriptor;

  private boolean warranty;

  private Date eventDate;
}
