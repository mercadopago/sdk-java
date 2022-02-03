package com.mercadopago.client.preference;

import lombok.Builder;
import lombok.Getter;

/** Differential pricing configuration. */
@Getter
@Builder
public class PreferenceDifferentialPricingRequest {
  /** Differential pricing ID. */
  private final Long id;
}
