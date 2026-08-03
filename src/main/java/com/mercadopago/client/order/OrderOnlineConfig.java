package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

// API version: 1ff4822a-2dfd-4393-800e-a562edb3fe32
/**
 * Configuration for online checkout behavior within an order. Defines redirect URLs for
 * different payment outcomes, differential pricing rules, and 3D Secure authentication settings.
 */
@Getter
@Builder
public class OrderOnlineConfig {

    /** URL called by MercadoPago to notify the integrator of payment status changes. */
    private String callbackUrl;

    /** URL where the buyer is redirected after a successful payment. */
    private String successUrl;

    /** URL where the buyer is redirected when the payment is pending. */
    private String pendingUrl;

    /** URL where the buyer is redirected when the payment fails. */
    private String failureUrl;

    /** URL for automatic return after payment completion. */
    private String autoReturnUrl;

    /**
     * Controls automatic redirect behavior after payment. "approved" redirects to success_url
     * on approval; "all" redirects on any outcome.
     */
    private String autoReturn;

    /**
     * ISO 8601 date-time from which the Order is available for payment.
     * Buyers cannot pay before this time.
     */
    private String availableFrom;

    /**
     * Restricts who can pay. "account_only" allows only logged-in Mercado Pago users.
     * Omit to accept all users.
     */
    private String allowedUserType;

    /** Tracking pixels for Google Ads and Facebook Ads conversion tracking. */
    private List<OrderTrackRequest> tracks;

    /** Payment retry configuration returned in the order response. */
    private OrderRetriesConfig retries;

    /** Differential pricing configuration for offering different conditions to specific buyers. */
    private OrderDifferentialPricing differentialPricing;

    /** Transaction security configuration for 3D Secure (3DS) authentication. */
    private OrderTransactionSecurity transactionSecurity;
}
