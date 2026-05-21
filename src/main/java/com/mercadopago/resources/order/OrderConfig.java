package com.mercadopago.resources.order;

import com.mercadopago.client.order.OrderOnlineConfig;
import com.mercadopago.client.order.OrderPaymentMethodConfig;
import lombok.Getter;

// API version: 1ff4822a-2dfd-4393-800e-a562edb3fe32

/**
 * Resource representing the configuration of a MercadoPago Order.
 * Groups together payment method restrictions, online checkout settings,
 * and point-of-sale terminal configuration.
 */
@Getter
public class OrderConfig {

    /** Payment method configuration defining accepted methods and installment rules. */
    private OrderPaymentMethodConfig paymentMethod;

    /** Online checkout configuration including redirect URLs and 3DS settings. */
    private OrderOnlineConfig online;

    /** Point-of-sale terminal configuration for in-store payments. */
    private OrderPointConfig point;

}
