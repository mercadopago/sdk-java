package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

// API version: b950ae02-4f49-4686-9ad3-7929b21b6495

/** OrderPaymentCreateRequest class. */
@Getter
@Builder
public class OrderPaymentRequest {

    /** Payment amount. */
    private String amount;

    /** Payment method information. */
    private OrderPaymentMethodRequest paymentMethod;

    /** Automatic Payments information. */
    private OrderAutomaticPaymentsRequest automaticPayments;

    /** Stored Credential information. */
    private OrderStoredCredentialRequest storedCredential;

    /** Subscription Data information. */
    private OrderSubscriptionDataRequest subscriptionData;
}
