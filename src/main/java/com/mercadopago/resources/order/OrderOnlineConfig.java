package com.mercadopago.resources.order;

import lombok.Getter;

/**
 * Order Online Configuration response.
 * Contains configuration for online orders including 3DS transaction security.
 */
@Getter
public class OrderOnlineConfig {

    /** Callback URL. */
    private String callbackUrl;

    /** Success URL. */
    private String successUrl;

    /** Pending URL. */
    private String pendingUrl;

    /** Failure URL. */
    private String failureUrl;

    /** Auto return URL. */
    private String autoReturnUrl;

    /** Differential pricing. */
    private OrderDifferentialPricing differentialPricing;

    /** Transaction security configuration for 3DS authentication. */
    private OrderTransactionSecurity transactionSecurity;
}
