package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;
// API version: 1ff4822a-2dfd-4393-800e-a562edb3fe32

/** OrderConfigRequest class. */
@Getter
@Builder
public class OrderConfigRequest {

    /** Payment method config. */
    private OrderPaymentMethodConfig paymentMethod;

    /** Online config. */
    private OrderOnlineConfig online;
}
