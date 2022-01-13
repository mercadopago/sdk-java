package com.mercadopago.client.common;

import lombok.Builder;
import lombok.Data;

/** AddressRequest class. */
@Data
@Builder
public class AddressRequest {
  private String zipCode;

  private String streetName;

  private String streetNumber;
}
