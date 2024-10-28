package com.mercadopago.resources.order;

import lombok.Getter;

/** OrderPayment class. */
@Getter
public class OrderPayment {

    /** Payment ID. */
    private String id;

    /** Payment amount. */
    private String amount;

    /** Payment currency. */
    private String currency;

    /** Payment status. */
    private String status;

    /** Payment status detail. */
    private String statusDetail;

    /** Reference to the order. */
    private OrderReference reference;

    /** Payment method information. */
    private OrderPaymentMethod paymentMethod;
}
