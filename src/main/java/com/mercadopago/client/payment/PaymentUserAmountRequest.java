package com.mercadopago.client.payment;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;

/**
 * Request object representing a user's amount details in a cross-currency payment.
 * Specifies the currency and transaction value from the perspective of
 * either the payer or the collector.
 */
@Getter
@Builder
public class PaymentUserAmountRequest {
    /** Currency identifier in ISO 4217 format (e.g. "USD", "BRL", "ARS"). */
    private String currencyId;

    /** Transaction amount in the specified currency. */
    private BigDecimal transaction;
}
