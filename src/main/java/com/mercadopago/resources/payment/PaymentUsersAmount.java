package com.mercadopago.resources.payment;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public class PaymentUsersAmount {
    /** Currency ID. ISO_4217 code. */
    private String currencyId;

    /** Transaction amount. */
    private BigDecimal transaction;
}
