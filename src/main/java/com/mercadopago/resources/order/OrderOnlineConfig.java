package com.mercadopago.resources.order;

import lombok.Getter;

/**
 * Resource representing the online checkout configuration of a MercadoPago Order.
 * Defines redirect URLs for each payment outcome, differential pricing rules,
 * and 3DS transaction security settings for card payments.
 */
@Getter
public class OrderOnlineConfig {

    /** URL called by MercadoPago to notify the integrator about payment status changes. */
    private String callbackUrl;

    /** URL where the payer is redirected after a successful payment. */
    private String successUrl;

    /** URL where the payer is redirected when the payment is pending approval. */
    private String pendingUrl;

    /** URL where the payer is redirected after a failed payment. */
    private String failureUrl;

    /** URL for automatic redirection after the checkout process completes. */
    private String autoReturnUrl;

    /**
     * Controls automatic redirect after payment. "approved" redirects to success_url on approval;
     * "all" redirects on any outcome.
     */
    private String autoReturn;

    /** ISO 8601 date-time from which the Order is available for payment. */
    private String availableFrom;

    /** Differential pricing configuration for applying custom pricing rules. */
    private OrderDifferentialPricing differentialPricing;

    /** Transaction security configuration for 3DS authentication on online payments. */
    private OrderTransactionSecurity transactionSecurity;
}
