package com.mercadopago.resources.order;

import lombok.Getter;

import java.util.List;

/**
 * Resource representing the refund result of an order transaction operation.
 * Contains the list of individual refund items generated when a transaction-level
 * refund is processed on a MercadoPago Order.
 */
@Getter
public class OrderTransactionRefund {

        /** List of individual refund items resulting from the transaction refund. */
        private List<OrderRefundItem> refunds;
}
