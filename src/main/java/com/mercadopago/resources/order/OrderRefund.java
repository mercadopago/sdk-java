package com.mercadopago.resources.order;

import com.mercadopago.net.MPResource;
import lombok.Getter;

// API version: d0494f1c-8d81-4c76-ae1d-0c65bb8ef6de

/**
 * Resource representing a refund issued against a payment within a MercadoPago Order.
 * Contains the refund amount, its current processing status, and references to
 * the originating transaction and associated items.
 */
@Getter
public class OrderRefund extends MPResource {

    /** Unique identifier of this refund. */
    private String id;

    /** Identifier of the original transaction that was refunded. */
    private String transactionId;

    /** External reference identifier for correlating this refund with the integrator's system. */
    private String referenceId;

    /** Refund amount expressed as a decimal string. */
    private String amount;

    /** Current processing status of the refund (e.g., "approved", "pending"). */
    private String status;

    /** Detailed sub-status providing additional context about the refund status. */
    private String statusDetail;

    /** Items associated with this refund. */
    private OrderItem items;

    /** End-to-end identifier for tracking the refund across payment systems. */
    private String e2eId;
}
