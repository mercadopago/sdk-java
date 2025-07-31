package com.mercadopago.client.preference;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PreferenceUserAmountRequest {
    /** Currency ID. ISO_4217 code. */
    private String currencyId;
    /** Transaction amount. */
    private BigDecimal transaction;
}
