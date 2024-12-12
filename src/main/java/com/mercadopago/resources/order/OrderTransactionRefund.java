package com.mercadopago.resources.order;

import lombok.Getter;

import java.util.List;

/**
 * OrderTransactionRefund class.
 */
@Getter
public class OrderTransactionRefund {

        /** Refunds information. */
        private List<OrderRefundItem> refunds;
}
