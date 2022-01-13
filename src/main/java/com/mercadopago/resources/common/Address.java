package com.mercadopago.resources.common;

import lombok.Data;

/** Address class. */
@Data
public class Address {
  public String zipCode;

  public String streetName;

  public String streetNumber;
}
