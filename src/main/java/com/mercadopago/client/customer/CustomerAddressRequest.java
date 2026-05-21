package com.mercadopago.client.customer;

import lombok.Builder;
import lombok.Getter;

/**
 * Request DTO representing a customer's default address. Used when creating or updating
 * a customer to specify their primary address information for shipping or billing purposes.
 *
 * @see <a href="https://www.mercadopago.com.br/developers/en/reference/customers">Customers API Reference</a>
 */
@Getter
@Builder
public class CustomerAddressRequest {
  /** Unique identifier of an existing address to set as default. */
  private final String id;

  /** Postal or ZIP code of the address. */
  private final String zipCode;

  /** Name of the street. */
  private final String streetName;

  /** Street number or house number. */
  private final Integer streetNumber;
}
