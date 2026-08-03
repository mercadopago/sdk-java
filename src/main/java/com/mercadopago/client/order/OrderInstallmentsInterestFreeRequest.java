package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * Request object for configuring interest-free installments in an order.
 * Defines the type and allowed installment counts for interest-free plans.
 */
@Getter
@Builder
public class OrderInstallmentsInterestFreeRequest {

    /**
     * Interest-free installment configuration type.
     * "range": defines a range via values[]. "list": explicit list of values. "none": disabled.
     */
    private String type;

    /**
     * Interest-free installment counts.
     * For type "range": [2, 6] means interest-free from 2x to 6x.
     * For type "list": explicit installment counts, e.g. [12].
     */
    private List<Integer> values;
}
