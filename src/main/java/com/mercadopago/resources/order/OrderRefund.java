package com.mercadopago.resources.order;

import lombok.Getter;

/** OrderRefund class. */
@Getter
public class OrderRefund {

    /** Refund ID. */
    private String id;

    /** Payment ID. */
    private String paymentId;

    /** Refund amount. */
    private String amount;

    /** Status of refund. */
    private String status;

    /** Reference of the refund. */
    private OrderReference reference;
}
