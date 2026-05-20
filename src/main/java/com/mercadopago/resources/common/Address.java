package com.mercadopago.resources.common;

import lombok.Getter;

/**
 * Represents a generic physical address used across the MercadoPago API.
 *
 * <p>Contains basic address information such as street name, street number, and zip code. This
 * class is used as a shared component in resources like payments, preferences, and customers.
 *
 * @see <a href="https://www.mercadopago.com.br/developers/en/reference">MercadoPago API Reference</a>
 */
@Getter
public class Address {
  /** Street name of the address. */
  private String streetName;

  /** Street number of the address. */
  private String streetNumber;

  /** Postal zip code of the address. */
  private String zipCode;
}
