package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentAmountsRequest {

    /** Payer amount. */
    private PaymentUserAmountRequest payer;

    /** Collector amount. */
    private PaymentUserAmountRequest collector;
}
