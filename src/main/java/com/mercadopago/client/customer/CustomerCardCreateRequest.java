package com.mercadopago.client.customer;

import com.mercadopago.resources.customer.CustomerCardIssuer;
import com.mercadopago.resources.datastructures.customer.card.Issuer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

/** Attributes used for a card creation request. */
@Getter
@Builder
public class CustomerCardCreateRequest {
  /** Card token sent by the frontend. */
  private String token;

  private CustomerCardIssuer issuer;

  private String paymentMethodId;
}
