package com.mercadopago.client.common;

import lombok.Builder;
import lombok.Getter;

/** AddressRequest class. */
@Getter
@Builder
public class AddressRequest {
  private final String zipCode;

  private final String streetName;

  private final String streetNumber;
}
