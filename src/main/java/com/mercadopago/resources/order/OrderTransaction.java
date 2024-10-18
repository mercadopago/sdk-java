package com.mercadopago.resources.order;

import lombok.Getter;

import java.util.List;

/** OrderTransaction class. */
@Getter
public class OrderTransaction {

    /** Payments information. */
    private List<OrderPayment> payments;

    /** Refunds information. */
    private List<OrderRefund> refunds;
}
