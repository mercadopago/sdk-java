package com.mercadopago.client.preference;

import lombok.Builder;
import lombok.Getter;

/**
 * Shipping method offered as free shipping within a checkout preference (applicable in
 * {@code me2} mode only).
 *
 * @see com.mercadopago.client.preference.PreferenceShipmentsRequest
 */
@Getter
@Builder
public class PreferenceFreeMethodRequest {
  /** Identifier of the shipping method to offer for free. */
  private final Long id;
}
