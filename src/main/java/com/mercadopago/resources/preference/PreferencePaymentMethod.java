package com.mercadopago.resources.preference;

import lombok.Getter;

/**
 * Resource representing a specific payment method referenced in a checkout preference.
 *
 * <p>Used to identify individual payment methods that are either excluded from or set as default
 * in the preference's payment configuration.
 *
 * @see PreferencePaymentMethods
 */
@Getter
public class PreferencePaymentMethod {
  /** Unique identifier of the payment method (e.g., visa, master, amex). */
  private String id;
}
