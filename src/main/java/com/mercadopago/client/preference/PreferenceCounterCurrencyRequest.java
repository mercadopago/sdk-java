package com.mercadopago.client.preference;

import lombok.Builder;
import lombok.Getter;

/**
 * Counter-currency configuration for a checkout preference, used for cross-currency transactions.
 *
 * @see com.mercadopago.client.preference.PreferenceRequest
 */
@Getter
@Builder
public class PreferenceCounterCurrencyRequest {
    /** Currency identifier in ISO 4217 format (e.g., USD, BRL). */
    private String currencyId;
}
