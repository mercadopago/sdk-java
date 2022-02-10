package com.mercadopago.client.customer;

import lombok.Builder;
import lombok.Getter;

/** Default address's information. */
@Getter
@Builder
public class CustomerAddressRequest {
  /** Address ID. */
  private final String id;

  /** Zip code. */
  private final String zipCode;

  /** Street name. */
  private final String streetName;

  /** Street number. */
  private final Integer streetNumber;
}
