package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;
// API version: 1ff4822a-2dfd-4393-800e-a562edb3fe32

/**
 * Request object for configuring an order's behavior. Groups payment method restrictions,
 * online checkout settings (URLs, 3DS, differential pricing), and Point-of-Sale terminal options.
 */
@Getter
@Builder
public class OrderConfigRequest {

    /** Payment method configuration including allowed types, installment limits, and defaults. */
    private OrderPaymentMethodConfig paymentMethod;

    /** Online checkout configuration with callback URLs and security settings. */
    private OrderOnlineConfig online;

    /** Point-of-Sale terminal configuration for in-person payments. */
    private OrderPointConfig point;
}
