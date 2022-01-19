package com.mercadopago.client.payment;

import com.mercadopago.client.common.IdentificationRequest;
import lombok.Builder;
import lombok.Getter;

/** PaymentPayerRequest class. */
@Getter
@Builder
public class PaymentPayerRequest {
  private final String type;

  private final String id;

  private final String email;

  private final IdentificationRequest identification;

  private final String firstName;

  private final String lastName;

  private final String entityType;
}
