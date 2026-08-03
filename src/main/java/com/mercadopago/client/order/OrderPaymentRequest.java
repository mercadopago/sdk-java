package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

// API version: 1ff4822a-2dfd-4393-800e-a562edb3fe32

/**
 * Request object representing a single payment within an order transaction. Defines the payment
 * amount, method, and optional recurring payment settings such as automatic payments,
 * stored credentials, and subscription data.
 */
@Getter
@Builder
public class OrderPaymentRequest {

    /** Payment amount as a decimal string. */
    private String amount;

    /** ISO 8601 duration or date-time indicating when this payment expires. */
    private String expirationTime;

    /**
     * Absolute date and time (ISO 8601) after which this payment can no longer be collected.
     * Distinct from expirationTime, which is a relative duration or TTL.
     * Type: String (ISO 8601, e.g. "2026-06-01T00:00:00.000-04:00").
     */
    private String dateOfExpiration;

    /** Payment method details including type, token, and installments. */
    private OrderPaymentMethodRequest paymentMethod;

    /** Configuration for automatic recurring payments. */
    private OrderAutomaticPaymentsRequest automaticPayments;

    /** Stored credential information for card-on-file transactions. */
    private OrderStoredCredentialRequest storedCredential;

    /** Subscription data for recurring billing scenarios. */
    private OrderSubscriptionDataRequest subscriptionData;
}
