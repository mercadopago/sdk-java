package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/** OrderTransactionRequest class. */
@Builder
@Getter
public class OrderTransactionRequest extends MPResource{

    /** List of payments. */
    private List<OrderPaymentRequest> payments;
}
