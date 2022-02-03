package com.mercadopago.resources.preference;

import java.math.BigDecimal;
import lombok.Getter;

/** Tax from preference. */
@Getter
public class PreferenceTax {
  /** Tax type. */
  private String type;

  /** Tax value. */
  private BigDecimal value;
}
