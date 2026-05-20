package com.mercadopago.resources.customer;

import lombok.Getter;

/**
 * Represents the default address of a customer in the MercadoPago API.
 *
 * <p>Contains a simplified set of address fields (zip code, street name, and street number) used
 * as the customer's primary address for shipping and billing purposes.
 *
 * @see Customer
 */
@Getter
public class CustomerDefaultAddress {

  /** Unique identifier of the default address. */
  private String id;

  /** Postal or zip code of the address. */
  private String zipCode;

  /** Street name of the address. */
  private String streetName;

  /** Street number of the address. */
  private Integer streetNumber;
}
