package com.mercadopago.resources.order;

import com.mercadopago.client.order.OrderOnlineConfig;
import com.mercadopago.client.order.OrderPaymentMethodConfig;
import lombok.Getter;

// API version: 1ff4822a-2dfd-4393-800e-a562edb3fe32

/** OrderConfig class. */
@Getter
public class OrderConfig {

    /** Payment method config. */
    private OrderPaymentMethodConfig paymentMethod;

    /** Online config. */
    private OrderOnlineConfig online;
    
}
