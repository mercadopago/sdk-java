package com.mercadopago.client.preference;

import lombok.Builder;
import lombok.Getter;

/**
 * Identifies a specific payment type to include or exclude from the checkout preference.
 *
 * @see com.mercadopago.client.preference.PreferencePaymentMethodsRequest
 */
@Getter
@Builder
public class PreferencePaymentTypeRequest {
  /** Unique identifier of the payment type (e.g., credit_card, ticket, bank_transfer). */
  private final String id;
}
