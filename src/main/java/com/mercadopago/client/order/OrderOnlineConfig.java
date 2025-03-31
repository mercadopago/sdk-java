package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

// API version: 1ff4822a-2dfd-4393-800e-a562edb3fe32
/** OrderOnlineConfig class. */
@Getter
@Builder
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
}
