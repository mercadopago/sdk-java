package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

// API version: 1ff4822a-2dfd-4393-800e-a562edb3fe32

/** OrderPaymentRequest class. */
@Getter
@Builder
public class OrderPaymentRequest {

    /** Payment amount. */
    private String amount;

    /** Payment expirationTime. */
    private String expirationTime;

    /** Payment method information. */
    private OrderPaymentMethodRequest paymentMethod;

    /** Automatic Payments information. */
    private OrderAutomaticPaymentsRequest automaticPayments;

    /** Stored Credential information. */
    private OrderStoredCredentialRequest storedCredential;

    /** Subscription Data information. */
    private OrderSubscriptionDataRequest subscriptionData;
}
