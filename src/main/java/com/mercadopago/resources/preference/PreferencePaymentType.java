package com.mercadopago.resources.preference;

import lombok.Getter;

/**
 * Resource representing a payment type referenced in a checkout preference.
 *
 * <p>Used to identify payment type categories (e.g., credit_card, debit_card, ticket) that can
 * be excluded from the checkout flow.
 *
 * @see PreferencePaymentMethods
 */
@Getter
public class PreferencePaymentType {
  /** Identifier of the payment type (e.g., credit_card, debit_card, ticket, atm). */
  private String id;
}
