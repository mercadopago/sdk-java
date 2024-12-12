package com.mercadopago.resources.order;

import com.mercadopago.net.MPResource;
import lombok.Getter;

/**
 * OrderRefund class.
 */
@Getter
public class OrderRefund extends MPResource {

    /** Refund id. */
    private String id;

    /** Refund status. */
    private String status;

    /** Refund status detail. */
    private String status_detail;

    /** Object Order Transaction Refund. */
    private OrderTransactionRefund transactions;
}
