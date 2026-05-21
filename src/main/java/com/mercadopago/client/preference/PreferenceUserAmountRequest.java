package com.mercadopago.client.preference;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;

/**
 * Amount details for a specific user (payer or collector) in a checkout preference, including
 * the currency and transaction value.
 *
 * @see com.mercadopago.client.preference.PreferenceAmountsRequest
 */
@Getter
@Builder
public class PreferenceUserAmountRequest {
    /** Currency identifier in ISO 4217 format (e.g., ARS, BRL). */
    private String currencyId;

    /** Transaction amount for this user. */
    private BigDecimal transaction;
}
