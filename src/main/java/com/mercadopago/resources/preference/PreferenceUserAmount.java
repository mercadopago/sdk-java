package com.mercadopago.resources.preference;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public class PreferenceUserAmount {
    /** Currency ID. ISO_4217 code. */
    private String currencyId;

    /** Transaction amount. */
    private BigDecimal transaction;
}
