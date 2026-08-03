package com.mercadopago.client.common;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Request DTO representing a physical address used across multiple Mercado Pago API resources such
 * as payments, customers, and shipping. Serves as a reusable base for address-related requests.
 *
 * @see <a href="https://www.mercadopago.com.br/developers/en/reference">Mercado Pago API Reference</a>
 */
@Getter
@SuperBuilder
public class AddressRequest {
  /** Postal or ZIP code of the address (e.g., "01310-100"). */
  private final String zipCode;

  /** Name of the street (e.g., "Avenida Paulista"). */
  private final String streetName;

  /** Street number or house number (e.g., "1578"). */
  private final String streetNumber;

  /** Neighborhood or district name. */
  private final String neighborhood;

  /** City name (e.g., "Sao Paulo"). */
  private final String city;

  /** State or province name (e.g., "SP"). */
  private final String state;

  /** Additional address details such as suite or unit number. */
  private final String complement;

  /** Floor number in the building, if applicable. */
  private final String floor;
}
