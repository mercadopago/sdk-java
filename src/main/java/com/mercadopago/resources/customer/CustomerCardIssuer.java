package com.mercadopago.resources.customer;

import lombok.Builder;
import lombok.Getter;

/** Card issuer details. */
@Getter
@Builder
public class CustomerCardIssuer {
  /** Id of the issuer. */
  private final String id;

  /** Name of the issuer. */
  private final String name;
}
