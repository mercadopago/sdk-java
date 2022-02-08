package com.mercadopago.client.customer;

import lombok.Builder;
import lombok.Getter;

/** Default address's information. */
@Getter
@Builder
public class CustomerAddressRequest {
  /** Address ID */
  private String id;

  /** Zip code */
  private String zipCode;

  /** Street name */
  private String streetName;

  /** Street number */
  private Integer streetNumber;
}
