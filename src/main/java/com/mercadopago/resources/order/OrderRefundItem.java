package com.mercadopago.resources.order;

import lombok.Getter;

/** OrderRefund class. */
@Getter
public class OrderRefundItem {

    /** Refund ID. */
    private String id;

    /** Transaction ID. */
    private String transactionId;

    /** Reference of the refund. */
    private String referenceId;

    /** Refund amount. */
    private String amount;

    /** Status of refund. */
    private String status;
}
