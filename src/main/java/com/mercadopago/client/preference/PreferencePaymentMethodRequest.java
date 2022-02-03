package com.mercadopago.client.preference;

import lombok.Builder;
import lombok.Getter;

/** Payment method information. */
@Getter
@Builder
public class PreferencePaymentMethodRequest {
  /** Payment method ID. */
  private final String id;
}
