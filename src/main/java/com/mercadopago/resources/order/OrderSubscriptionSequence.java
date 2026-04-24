package com.mercadopago.resources.order;

import lombok.Builder;
import lombok.Getter;

// API version: 1ff4822a-2dfd-4393-800e-a562edb3fe32

/**
 * Resource representing the sequence position of a payment within a subscription cycle.
 * Tracks the current payment number relative to the total number of planned payments.
 */
@Getter
@Builder
public class OrderSubscriptionSequence {

    /** Current sequence number of this payment in the subscription (1-based). */
    private Integer number;

    /** Total number of planned payments in the subscription cycle. */
    private Integer total;

}
