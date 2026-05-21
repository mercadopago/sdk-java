package com.mercadopago.client.preference;

import lombok.Builder;
import lombok.Getter;

/**
 * Identifies a specific payment method to include or exclude from the checkout preference.
 *
 * @see com.mercadopago.client.preference.PreferencePaymentMethodsRequest
 */
@Getter
@Builder
public class PreferencePaymentMethodRequest {
  /** Unique identifier of the payment method (e.g., visa, master, amex). */
  private final String id;
}
