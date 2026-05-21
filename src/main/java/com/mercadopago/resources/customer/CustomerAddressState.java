package com.mercadopago.resources.customer;

import lombok.Getter;

/**
 * Represents state or province information within a customer's address in the MercadoPago API.
 *
 * <p>Contains the state identifier and name as part of the geographic breakdown of a customer
 * address.
 *
 * @see CustomerAddress
 */
@Getter
public class CustomerAddressState {

  /** Unique identifier of the state or province. */
  private String id;

  /** Name of the state or province. */
  private String name;
}
