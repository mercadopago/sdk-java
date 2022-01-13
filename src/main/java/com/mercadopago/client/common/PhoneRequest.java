package com.mercadopago.client.common;

import lombok.Builder;
import lombok.Data;

/** PhoneRequest class. */
@Data
@Builder
public class PhoneRequest {
  private String areaCode;

  private String number;
}
