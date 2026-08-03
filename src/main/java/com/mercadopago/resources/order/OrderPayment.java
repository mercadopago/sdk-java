package com.mercadopago.resources.order;

import java.util.List;
import lombok.Getter;

// API version: d0494f1c-8d81-4c76-ae1d-0c65bb8ef6de

/**
 * Resource representing a single payment within a MercadoPago Order transaction.
 * Contains the payment amount, method details, lifecycle status, and related data
 * such as attempts, automatic payment configuration, and applied discounts.
 */
@Getter
public class OrderPayment {

    /** Payment amount expressed as a decimal string. */
    private String amount;

    /** Payment method details including type, card, installments, and ticket information. */
    private OrderPaymentMethod paymentMethod;

    /** Unique identifier of this payment. */
    private String id;

    /** Reference identifier linking this payment to an external system. */
    private String referenceId;

    /** Current status of the payment (e.g., "approved", "pending", "rejected"). */
    private String status;

    /** Detailed sub-status providing additional context about the payment status. */
    private String statusDetail;

    /** ISO 8601 date-time when the payment expires if not completed. */
    private String dateOfExpiration;

    /** Duration in ISO 8601 format after which the payment expires. */
    private String expirationTime;

    /** Amount already paid on this payment, expressed as a decimal string. */
    private String paidAmount;

    /** Sequential number of the current payment attempt. */
    private Integer attemptNumber;

    /** Details of previous payment attempts made for this payment. */
    private OrderAttempts attempts;

    /** Configuration for automatic recurring payments. */
    private OrderAutomaticPayments automaticPayments;

    /** Stored credential information for card-on-file transactions. */
    private OrderStoredCredential storedCredential;

    /** Subscription data when this payment is part of a recurring billing cycle. */
    private OrderSubscriptionData subscriptionData;

    /** Total amount that has been refunded on this payment, expressed as a decimal string. */
    private String refundedAmount;

    /** Payment provider or acquirer that processed this payment. */
    private String provider;

    /** List of discounts applied to this payment. */
    private List<OrderPaymentDiscount> discounts;
}
