package com.mercadopago.resources.order;

import lombok.Getter;

// API version: d0494f1c-8d81-4c76-ae1d-0c65bb8ef6de

/** OrderPayment class. */
@Getter
public class OrderPayment {

    /** Payment amount. */
    private String amount;

    /** Payment method information. */
    private OrderPaymentMethod paymentMethod;

    /** Payment ID. */
    private String id;

    /** Reference ID. */
    private String referenceId;

    /** Payment status. */
    private String status;

    /** Payment status detail. */
    private String statusDetail;

    /** Payment date of expiration. */
    private String dateOfExpiration;

    /** Payment Expiration Time. */
    private String expirationTime;

    /** Payment Paid Amount. */
    private String paidAmount;

    /** Payment Attempt Number. */
    private Integer attemptNumber;

    /** Payment Attempts. */
    private OrderAttempts attempts;

    /** Automatic Payments information. */
    private OrderAutomaticPayments automaticPayments;

    /** Stored Credential information. */
    private OrderStoredCredential storedCredential;

    /** Subscription Data information. */
    private OrderSubscriptionData subscriptionData;
}
