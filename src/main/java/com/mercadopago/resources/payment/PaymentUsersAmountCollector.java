package com.mercadopago.resources.payment;

import java.math.BigDecimal;

import lombok.Getter;

/**
 * Resource that represents the amount breakdown from the collector's (seller's) perspective
 * in a MercadoPago payment.
 *
 * <p>Contains the transaction amount and net received amount in the collector's currency,
 * useful for cross-currency marketplace reconciliation.
 *
 * @see PaymentAmounts#getCollector()
 */
@Getter
public class PaymentUsersAmountCollector {
    /** ISO 4217 currency code of the collector's settlement currency. */
    private String currencyId;

    /** Gross transaction amount in the collector's currency. */
    private BigDecimal transaction;

    /** Net amount received by the collector after all fees are deducted. */
    private BigDecimal netReceived;
}
