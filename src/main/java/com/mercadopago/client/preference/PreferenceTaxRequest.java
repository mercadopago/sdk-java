package com.mercadopago.client.preference;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

/** Tax from preference. */
@Getter
@Builder
public class PreferenceTaxRequest {
  /** Tax type. */
  private final String type;

  /** Tax value. */
  private final BigDecimal value;
}
