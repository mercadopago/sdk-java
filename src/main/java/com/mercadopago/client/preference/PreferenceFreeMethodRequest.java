package com.mercadopago.client.preference;

import lombok.Builder;
import lombok.Getter;

/** Shipping free method information. */
@Getter
@Builder
public class PreferenceFreeMethodRequest {
  /** Shipping method ID. */
  private final Long id;
}
