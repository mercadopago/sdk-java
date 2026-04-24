package com.mercadopago.resources.order;

import lombok.Builder;
import lombok.Getter;

// API version: 1ff4822a-2dfd-4393-800e-a562edb3fe32

/**
 * Resource representing the billing period for a subscription invoice
 * within a MercadoPago Order. Defines the recurrence type and interval
 * between charges.
 */
@Getter
@Builder
public class OrderInvoicePeriod {

    /** Type of billing period (e.g., "daily", "monthly", "yearly"). */
    private String type;

    /** Number of intervals between billing cycles (e.g., 1 for every month, 2 for bimonthly). */
    private Integer period;

}
