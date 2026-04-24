package com.mercadopago.resources.customer;

import lombok.Getter;

/**
 * Represents country information within a customer's address in the MercadoPago API.
 *
 * <p>Contains the country identifier and name as part of the geographic breakdown of a customer
 * address.
 *
 * @see CustomerAddress
 */
@Getter
public class CustomerAddressCountry {
  /** Unique identifier of the country. */
  private String id;

  /** Name of the country. */
  private String name;
}
