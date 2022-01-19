package com.mercadopago.client.common;

import lombok.Builder;
import lombok.Getter;

/** PhoneRequest class. */
@Getter
@Builder
public class PhoneRequest {
  private final String areaCode;

  private final String number;
}
