package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

// API version: b950ae02-4f49-4686-9ad3-7929b21b6495

/** OrderTransactionRequest class. */
@Builder
@Getter
public class OrderTransactionRequest{

    /** List of payments. */
    private List<OrderPaymentRequest> payments;
}
