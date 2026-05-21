package com.mercadopago.resources.preference;

import java.math.BigDecimal;
import lombok.Getter;

/**
 * Resource representing a tax applied to a checkout preference.
 *
 * <p>Defines a tax entry with its type and monetary value. Multiple taxes can be associated with
 * a single preference to cover different tax obligations (e.g., IVA, IIBB).
 *
 * @see Preference
 */
@Getter
public class PreferenceTax {
  /** Tax type identifier (e.g., IVA, INC). */
  private String type;

  /** Monetary value of the tax. */
  private BigDecimal value;
}
