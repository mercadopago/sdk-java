package com.mercadopago.client.preference;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

/**
 * Tax item applied to a checkout preference, specifying the tax type and its monetary value.
 *
 * @see com.mercadopago.client.preference.PreferenceRequest
 */
@Getter
@Builder
public class PreferenceTaxRequest {
  /** Type of tax (e.g., IVA, INC). */
  private final String type;

  /** Monetary value of the tax. */
  private final BigDecimal value;
}
