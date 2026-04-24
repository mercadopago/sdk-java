package com.mercadopago.resources.customer;

import lombok.Getter;

/**
 * Represents neighborhood information within a customer's address in the MercadoPago API.
 *
 * <p>Contains the neighborhood identifier and name as part of the geographic breakdown of a
 * customer address.
 *
 * @see CustomerAddress
 */
@Getter
public class CustomerAddressNeighborhood {
  /** Unique identifier of the neighborhood. */
  private String id;

  /** Name of the neighborhood. */
  private String name;
}
