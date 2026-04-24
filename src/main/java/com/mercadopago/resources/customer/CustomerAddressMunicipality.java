package com.mercadopago.resources.customer;

import lombok.Getter;

/**
 * Represents municipality information within a customer's address in the MercadoPago API.
 *
 * <p>Contains the municipality identifier and name as part of the geographic breakdown of a
 * customer address. Applicable in countries where municipalities are an administrative division.
 *
 * @see CustomerAddress
 */
@Getter
public class CustomerAddressMunicipality {
  /** Unique identifier of the municipality. */
  private String id;

  /** Name of the municipality. */
  private String name;
}
