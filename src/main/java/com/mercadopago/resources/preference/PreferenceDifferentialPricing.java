package com.mercadopago.resources.preference;

import lombok.Getter;

/**
 * Resource representing the differential pricing configuration of a checkout preference.
 *
 * <p>References a pre-configured differential pricing rule by its identifier. When applied, it
 * enables special pricing conditions for specific buyer segments or payment methods.
 *
 * @see Preference
 */
@Getter
public class PreferenceDifferentialPricing {
  /** Unique identifier of the differential pricing rule to apply. */
  private Long id;
}
