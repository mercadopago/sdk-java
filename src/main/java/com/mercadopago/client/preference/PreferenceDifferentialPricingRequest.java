package com.mercadopago.client.preference;

import lombok.Builder;
import lombok.Getter;

/**
 * Differential pricing configuration for a checkout preference, allowing specific pricing rules
 * per buyer segment.
 *
 * @see com.mercadopago.client.preference.PreferenceRequest
 */
@Getter
@Builder
public class PreferenceDifferentialPricingRequest {
  /** Identifier of the differential pricing rule to apply. */
  private final Long id;
}
