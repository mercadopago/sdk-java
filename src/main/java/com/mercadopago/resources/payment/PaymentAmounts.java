package com.mercadopago.resources.payment;

import lombok.Getter;

@Getter
public class PaymentAmounts {
    /** Payer amount. */
    private PaymentUsersAmount payer;

    /** Collector amount. */
    private PaymentUsersAmount collector;
}
