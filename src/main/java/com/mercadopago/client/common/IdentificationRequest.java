package com.mercadopago.client.common;

import lombok.Builder;
import lombok.Getter;

/** IdentificationRequest class. */
@Getter
@Builder
public class IdentificationRequest {
  /** Identification type. */
  private final String type;

  /** Identification number. */
  private final String number;
}
