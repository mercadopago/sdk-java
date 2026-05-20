package com.mercadopago.resources.preference;

import lombok.Getter;

/**
 * Resource representing the counter currency configuration for cross-currency payments.
 *
 * <p>Specifies the alternative currency in which the payment amount is displayed or settled
 * when it differs from the preference's primary currency.
 *
 * @see Preference
 */
@Getter
public class PreferenceCounterCurrency {
    /** ISO 4217 currency code of the counter currency (e.g., USD, EUR). */
    private String currencyId;
}
