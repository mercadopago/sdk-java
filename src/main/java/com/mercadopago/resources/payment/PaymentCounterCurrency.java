package com.mercadopago.resources.payment;

import java.math.BigDecimal;

import lombok.Getter;

/**
 * Resource that holds counter-currency conversion details for a MercadoPago payment.
 *
 * <p>When the payment currency differs from the collector's settlement currency, this resource
 * provides the exchange rate, converted amount, and any refunded amount in the counter currency.
 *
 * @see Payment#getCounterCurrency()
 */
@Getter
public class PaymentCounterCurrency {
    /** ISO 4217 currency code of the counter currency (e.g. USD, EUR). */
    private String currencyId;
    /** Exchange rate applied to convert from the payment currency to the counter currency. */
    private BigDecimal rate;
    /** Payment amount expressed in the counter currency. */
    private BigDecimal amount;
    /** Refunded amount expressed in the counter currency. */
    private BigDecimal amountRefunded;
}
