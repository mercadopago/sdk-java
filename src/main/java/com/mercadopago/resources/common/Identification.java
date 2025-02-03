package com.mercadopago.resources.common;

import lombok.Getter;

// API version: b950ae02-4f49-4686-9ad3-7929b21b6495

/** Base type that represents identifications such as customer identification. */
@Getter
public class Identification {
  /** Type of identification. */
  private String type;

  /** Unique number of that identification. */
  private String number;
}
