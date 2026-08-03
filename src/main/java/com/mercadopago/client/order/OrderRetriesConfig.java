package com.mercadopago.client.order;

import lombok.Getter;

/**
 * Configuration representing the payment retry behavior for an order.
 */
@Getter
public class OrderRetriesConfig {

    /** Whether automatic payment retry is enabled. When false, a failed attempt ends the order. */
    private Boolean allowed;
}
