package com.mercadopago.resources.customer;

import java.time.OffsetDateTime;
import lombok.Getter;

/** Customer's address. */
@Getter
public class CustomerAddress {

  /** Address ID. */
  private String id;

  /** Phone number. */
  private String phone;

  /** Address name. */
  private String name;

  /** Floor. */
  private String floor;

  /** Apartment. */
  private String apartment;

  /** Street name. */
  private String streetName;

  /** Street number. */
  private String streetNumber;

  /** Postal code. */
  private String zipCode;

  /** City information. */
  private CustomerAddressCity city;

  /** State information. */
  private CustomerAddressState state;

  /** Country information. */
  private CustomerAddressCountry country;

  /** Neighborhood information. */
  private CustomerAddressNeighborhood neighborhood;

  /** Municipality information. */
  private CustomerAddressMunicipality municipality;

  /** Additional info. */
  private String comments;

  /** Address date of creation. */
  private OffsetDateTime dateCreated;
}
