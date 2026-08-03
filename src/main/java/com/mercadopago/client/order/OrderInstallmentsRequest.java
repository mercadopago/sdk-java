package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

/**
 * Request object for installment configuration in an order.
 */
@Getter
@Builder
public class OrderInstallmentsRequest {

    /** Interest-free installment configuration. */
    private OrderInstallmentsInterestFreeRequest interestFree;
}
