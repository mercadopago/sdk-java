package com.mercadopago.resources.order;

import lombok.Getter;

// API version: b950ae02-4f49-4686-9ad3-7929b21b6495

/** OrderPayment class. */
@Getter
public class OrderPayment {

    /** Payment ID. */
    private String id;

    /** Reference ID. */
    private String referenceId;

    /** Payment status. */
    private String status;

    /** Payment amount. */
    private String amount;

    /** Payment method information. */
    private OrderPaymentMethod paymentMethod;
}
