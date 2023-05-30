package com.mercadopago.client.common;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/** AddressRequest class. */
@Getter
@SuperBuilder
public class AddressRequest {
  /** Zip code. */
  private final String zipCode;

  /** Street name. */
  private final String streetName;

  /** Street number. */
  private final String streetNumber;
}
