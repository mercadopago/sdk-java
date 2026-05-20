package com.mercadopago.resources.order;

import lombok.Getter;

/**
 * Resource representing an individual refund line item within a MercadoPago Order transaction refund.
 * Contains details about a single refund entry including its amount, status, and
 * references to the originating transaction.
 */
@Getter
public class OrderRefundItem {

    /** Unique identifier of this refund item. */
    private String id;

    /** Identifier of the original transaction being refunded. */
    private String transactionId;

    /** External reference identifier for correlating this refund with the integrator's system. */
    private String referenceId;

    /** Refund amount expressed as a decimal string. */
    private String amount;

    /** Current processing status of this refund item (e.g., "approved", "pending"). */
    private String status;
}
