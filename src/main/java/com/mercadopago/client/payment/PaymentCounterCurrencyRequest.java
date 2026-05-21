package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/**
 * Request object specifying the counter-currency for cross-border payments.
 * Indicates the currency in which the collector will receive the funds when
 * it differs from the payer's currency.
 */
@Getter
@Builder
public class PaymentCounterCurrencyRequest {
    /** Currency identifier in ISO 4217 format (e.g. "USD", "BRL"). */
    private String currencyId;
}
