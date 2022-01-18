package com.mercadopago.client.customer;

import lombok.AllArgsConstructor;
import lombok.Data;

/** Attributes used for a card creation request. */
@Data
@AllArgsConstructor
public class CustomerCardCreateRequest {
  /** Card token sent by the frontend. */
  private String token;
}
