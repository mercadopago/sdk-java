package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

// API version: 1ff4822a-2dfd-4393-800e-a562edb3fe32

/**
 * Request object representing the transaction data within an order. A transaction groups one or
 * more payments that are processed together as part of the same order.
 */
@Builder
@Getter
public class OrderTransactionRequest{

    /** List of payments associated with this transaction. */
    private List<OrderPaymentRequest> payments;
}
