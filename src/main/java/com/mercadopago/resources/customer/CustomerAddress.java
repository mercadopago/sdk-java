package com.mercadopago.resources.customer;

import java.time.OffsetDateTime;
import lombok.Getter;

/**
 * Represents a detailed address associated with a customer in the MercadoPago API.
 *
 * <p>Contains full address information including street, apartment, floor, city, state, country,
 * neighborhood, and municipality. A customer may have multiple addresses on file.
 *
 * @see Customer
 * @see <a href="https://www.mercadopago.com.br/developers/en/reference/customers/_customers/post">
 *     Customer API Reference</a>
 */
@Getter
public class CustomerAddress {

  /** Unique identifier of the address. */
  private String id;

  /** Contact phone number associated with this address. */
  private String phone;

  /** Descriptive name or label for this address (e.g., "Home", "Work"). */
  private String name;

  /** Floor number within the building. */
  private String floor;

  /** Apartment or unit number. */
  private String apartment;

  /** Street name of the address. */
  private String streetName;

  /** Street number of the address. */
  private String streetNumber;

  /** Postal or zip code of the address. */
  private String zipCode;

  /** City information of the address. */
  private CustomerAddressCity city;

  /** State or province information of the address. */
  private CustomerAddressState state;

  /** Country information of the address. */
  private CustomerAddressCountry country;

  /** Neighborhood information of the address. */
  private CustomerAddressNeighborhood neighborhood;

  /** Municipality information of the address. */
  private CustomerAddressMunicipality municipality;

  /** Additional comments or instructions about the address. */
  private String comments;

  /** Date and time when the address was created. */
  private OffsetDateTime dateCreated;
}
