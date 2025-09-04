package com.mercadopago.resources.payment;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public class PaymentCounterCurrency {
    /** Currency ID. ISO_4217 code. */
    private String currencyId;
    private BigDecimal rate;
    private BigDecimal amount;
    private BigDecimal amountRefunded;
}
