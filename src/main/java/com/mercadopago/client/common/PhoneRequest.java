package com.mercadopago.client.common;

import lombok.Builder;
import lombok.Getter;

/** PhoneRequest class. */
@Getter
@Builder
public class PhoneRequest {
  /** Area code. */
  private final String areaCode;

  /** Phone number. */
  private final String number;
}
