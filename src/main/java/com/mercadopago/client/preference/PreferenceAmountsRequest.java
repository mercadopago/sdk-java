package com.mercadopago.client.preference;

import lombok.Builder;
import lombok.Getter;

/**
 * Amount breakdown for a checkout preference, specifying payer and collector amounts along with
 * optional counter-currency details.
 *
 * @see com.mercadopago.client.preference.PreferenceRequest
 */
@Getter
@Builder
public class PreferenceAmountsRequest {
    /** Counter-currency conversion details. */
    private PreferenceCounterCurrencyRequest counterCurrency;

    /** Amount information from the payer's perspective. */
    private PreferenceUserAmountRequest payer;

    /** Amount information from the collector's perspective. */
    private PreferenceUserAmountRequest collector;
}