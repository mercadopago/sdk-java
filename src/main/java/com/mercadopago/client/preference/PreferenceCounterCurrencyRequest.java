package com.mercadopago.client.preference;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PreferenceCounterCurrencyRequest {
    /** Currency ID. ISO_4217 code. */
    private String currencyId;
}
