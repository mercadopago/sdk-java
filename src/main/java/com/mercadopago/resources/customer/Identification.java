package com.mercadopago.resources.datastructures.customer;

import lombok.Getter;

/** Customer identification. */
@Getter
public class Identification {

  /** Type of identication */
  private String type;

  /** Identification number*/
  private String number;
}
