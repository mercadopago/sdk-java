package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

/**
 * Request object representing a single payment to be refunded within an order refund operation.
 * Specifies which payment to refund and the refund amount.
 */
@Builder
@Getter
public class OrderRefundPaymentRequest {

    /** Identifier of the payment to refund. */
    private String id;

    /** Amount to refund as a decimal string. Use {@code null} for a full refund. */
    private String amount;
}



