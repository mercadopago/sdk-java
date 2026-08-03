package com.mercadopago.resources.preference;

import java.math.BigDecimal;

import lombok.Getter;

/**
 * Resource representing the amount details for a specific party (payer or collector) in a
 * checkout preference.
 *
 * <p>Contains the currency and transaction amount applicable to the party. Used within
 * {@link PreferenceAmounts} to break down the total preference amount by role.
 *
 * @see PreferenceAmounts
 */
@Getter
public class PreferenceUserAmount {
    /** ISO 4217 currency code for the transaction amount (e.g., ARS, BRL, USD). */
    private String currencyId;

    /** Transaction amount applicable to this party. */
    private BigDecimal transaction;
}
