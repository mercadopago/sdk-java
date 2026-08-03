package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/**
 * Request object defining the payer and collector amounts for a payment.
 * Used in cross-currency payment scenarios where the payer and the collector
 * may operate in different currencies.
 */
@Getter
@Builder
public class PaymentAmountsRequest {

    /** Amount details from the payer's perspective, including currency and transaction value. */
    private PaymentUserAmountRequest payer;

    /** Amount details from the collector's perspective, including currency and transaction value. */
    private PaymentUserAmountRequest collector;
}
