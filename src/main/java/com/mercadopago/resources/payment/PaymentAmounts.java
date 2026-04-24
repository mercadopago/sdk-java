package com.mercadopago.resources.payment;

import lombok.Getter;

/**
 * Resource that holds the amount breakdown for both the payer and collector in a MercadoPago payment.
 *
 * <p>Provides currency-specific transaction and net amounts from the perspective of each party
 * involved in the payment, useful for cross-currency and marketplace scenarios.
 *
 * @see Payment#getAmounts()
 */
@Getter
public class PaymentAmounts {
    /** Amount details from the payer's perspective, including total paid and currency. */
    private PaymentUsersAmountPayer payer;

    /** Amount details from the collector's perspective, including net received and currency. */
    private PaymentUsersAmountCollector collector;
}
