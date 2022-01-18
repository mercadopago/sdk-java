package com.mercadopago.client.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

/** Attributes used for a card creation request. */
@Builder
public class CustomerCardCreateRequest {
  /** Card token sent by the frontend. */
  @Getter private String token;
}
