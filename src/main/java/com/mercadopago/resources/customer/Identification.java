package com.mercadopago.resources.customer;

import lombok.Getter;

/** Customer identification. */
@Getter
public class Identification {

  /** Type of identification. */
  private String type;

  /** Identification number. */
  private String number;
}
