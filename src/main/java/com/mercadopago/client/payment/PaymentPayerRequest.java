package com.mercadopago.client.payment;

import com.mercadopago.client.common.IdentificationRequest;
import lombok.Builder;
import lombok.Data;

/** PaymentPayerRequest class. */
@Data
@Builder
public class PaymentPayerRequest {
  private String type;

  private String id;

  private String email;

  private IdentificationRequest identification;

  private String firstName;

  private String lastName;

  private String entityType;
}
