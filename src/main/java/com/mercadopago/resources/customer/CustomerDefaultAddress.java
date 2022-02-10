package com.mercadopago.resources.customer;

import lombok.Getter;

/** Default address details for customer. */
@Getter
public class CustomerDefaultAddress {

  /** Address Id. */
  private String id;

  /** Address zip code. */
  private String zipCode;

  /** Street name. */
  private String streetName;

  /** Street number. */
  private Integer streetNumber;
}
