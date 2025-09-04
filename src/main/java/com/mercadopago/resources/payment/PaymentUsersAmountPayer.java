package com.mercadopago.resources.payment;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public class PaymentUsersAmountPayer {
    /** Currency ID. ISO_4217 code. */
    private String currencyId;

    /** Transaction amount. */
    private BigDecimal transaction;

    /** Total paid amount. */
    private BigDecimal totalPaid;
}
