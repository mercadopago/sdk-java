package com.mercadopago.client.preference;

import lombok.Builder;
import lombok.Getter;

/** Payment type information. */
@Getter
@Builder
public class PreferencePaymentTypeRequest {
  /** Payment type ID. */
  private final String id;
}
