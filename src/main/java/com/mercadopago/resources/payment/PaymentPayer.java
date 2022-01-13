package com.mercadopago.resources.payment;

import com.mercadopago.resources.common.Identification;
import lombok.Builder;
import lombok.Data;

/** PaymentPayer class. */
@Data
@Builder
public class PaymentPayer {
  private String type;

  private String id;

  private String email;

  private Identification identification;

  private String firstName;

  private String lastName;

  private String entityType;
}
