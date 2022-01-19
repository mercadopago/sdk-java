package com.mercadopago.resources.customer;

import lombok.Data;

/** Card issuer details. */
@Data
public class CustomerCardIssuer {
  /** Id of the issuer. */
  private Integer id;

  /** Name of the issuer. */
  private String name;
}
