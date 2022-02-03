package com.mercadopago.resources.common;

import lombok.Getter;

/** Address class. */
@Getter
public class Address {
  /** Zip code. */
  private String zipCode;

  /** Street name. */
  private String streetName;

  /** Street number. */
  private String streetNumber;
}
