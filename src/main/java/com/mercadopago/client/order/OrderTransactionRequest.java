package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

// API version: 1ff4822a-2dfd-4393-800e-a562edb3fe32

/** OrderTransactionRequest class. */
@Builder
@Getter
public class OrderTransactionRequest{

    /** List of payments. */
    private List<OrderPaymentRequest> payments;
}
