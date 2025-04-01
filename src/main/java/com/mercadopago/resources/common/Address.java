package com.mercadopago.resources.common;

import lombok.Getter;

/** Address class. */
@Getter
public class Address {
  /** Street name. */
  private String streetName;

  /** Street number. */
  private String streetNumber;

  /** Zip Code. */
  private String zipCode;
}
