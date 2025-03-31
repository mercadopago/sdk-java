package com.mercadopago.resources.common;

import lombok.Getter;

/** Base type that represents identifications such as customer identification. */
@Getter
public class Identification {
  /** Type of identification. */
  private String type;

  /** Unique number of that identification. */
  private String number;
}
