package com.mercadopago.client.customer;

import com.mercadopago.resources.customer.CustomerCardIssuer;
import lombok.Builder;
import lombok.Getter;

/** Attributes used for a card creation request. */
@Getter
@Builder
public class CustomerCardCreateRequest {
  /** Card token sent by the frontend. */
  private final String token;

  private final CustomerCardIssuer issuer;

  private final String paymentMethodId;
}
