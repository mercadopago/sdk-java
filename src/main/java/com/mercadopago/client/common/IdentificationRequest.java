package com.mercadopago.client.common;

import lombok.Builder;
import lombok.Getter;

/** IdentificationRequest class. */
@Getter
@Builder
public class IdentificationRequest {
  private final String type;

  private final String number;
}
