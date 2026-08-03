package com.mercadopago.resources.customer;

import lombok.Getter;

/**
 * Represents city information within a customer's address in the MercadoPago API.
 *
 * <p>Contains the city identifier and name as part of the geographic breakdown of a customer
 * address.
 *
 * @see CustomerAddress
 */
@Getter
public class CustomerAddressCity {
  /** Unique identifier of the city. */
  private String id;

  /** Name of the city. */
  private String name;
}
