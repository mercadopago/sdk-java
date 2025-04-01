package com.mercadopago.resources.order;

import lombok.Builder;
import lombok.Getter;

// API version: 1ff4822a-2dfd-4393-800e-a562edb3fe32

/** OrderSubscriptionData class. */
@Getter
@Builder
public class OrderSubscriptionData {

    /** Subscription Sequence. */
    private OrderSubscriptionSequence subscriptionSequence;

    /** Invoice ID. */
    private String invoiceId;

    /** Invoice Period. */
    private OrderInvoicePeriod invoicePeriod;

    /** Billing Date. */
    private String billingDate;

}
