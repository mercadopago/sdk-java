package com.mercadopago.client.preference;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PreferenceAmountsRequest {
    /** Counter currency. */
    private PreferenceCounterCurrencyRequest counterCurrency;

    /** Payer amount. */
    private PreferenceUserAmountRequest payer;

    /** Collector amount. */
    private PreferenceUserAmountRequest collector;
}