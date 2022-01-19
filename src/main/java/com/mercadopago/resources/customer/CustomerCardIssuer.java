package com.mercadopago.resources.customer;

import lombok.Getter;

/** Card issuer details. */
@Getter
public class CustomerCardIssuer {
  /** Id of the issuer. */
  private Integer id;

  /** Name of the issuer. */
  private String name;
}
