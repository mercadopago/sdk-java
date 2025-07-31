package com.mercadopago.resources.preference;

import lombok.Getter;

@Getter
public class PreferenceAmounts {
    /** Payer amount. */
    private PreferenceUserAmount payer;

    /** Collector amount. */
    private PreferenceUserAmount collector;
}
