package com.mercadopago.resources.order;

import lombok.Builder;
import lombok.Getter;

// API version: 1ff4822a-2dfd-4393-800e-a562edb3fe32

/**
 * Resource representing subscription billing data for a recurring MercadoPago Order payment.
 * Contains the subscription sequence position, invoice details, billing period,
 * and the scheduled billing date.
 */
@Getter
@Builder
public class OrderSubscriptionData {

    /** Sequence information indicating the position of this payment in the subscription cycle. */
    private OrderSubscriptionSequence subscriptionSequence;

    /** Unique identifier of the invoice associated with this billing cycle. */
    private String invoiceId;

    /** Billing period configuration defining the recurrence interval. */
    private OrderInvoicePeriod invoicePeriod;

    /** ISO 8601 date when this subscription payment is billed. */
    private String billingDate;

}
