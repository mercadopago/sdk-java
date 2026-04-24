package com.mercadopago.resources.payment;

import java.math.BigDecimal;

import lombok.Getter;

/**
 * Resource that represents the amount breakdown from the payer's (buyer's) perspective
 * in a MercadoPago payment.
 *
 * <p>Contains the transaction amount and total paid amount in the payer's currency,
 * which may include fees and financing costs.
 *
 * @see PaymentAmounts#getPayer()
 */
@Getter
public class PaymentUsersAmountPayer {
    /** ISO 4217 currency code of the payer's currency. */
    private String currencyId;

    /** Transaction amount in the payer's currency before additional charges. */
    private BigDecimal transaction;

    /** Total amount paid by the buyer, including fees and financing costs. */
    private BigDecimal totalPaid;
}
