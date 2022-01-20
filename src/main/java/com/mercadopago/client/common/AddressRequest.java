package com.mercadopago.client.common;

import lombok.Builder;
import lombok.Getter;

/** AddressRequest class. */
@Getter
@Builder
public class AddressRequest {
  /** Zip code. */
  private final String zipCode;

  /** Street name. */
  private final String streetName;

  /** Street number. */
  private final String streetNumber;
}
