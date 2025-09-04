package com.mercadopago.resources.payment;

import lombok.Getter;

@Getter
public class PaymentAmounts {
    /** Payer amount. */
    private PaymentUsersAmountPayer payer;

    /** Collector amount. */
    private PaymentUsersAmountCollector collector;
}
