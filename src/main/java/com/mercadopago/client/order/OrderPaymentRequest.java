package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

/** OrderPaymentCreateRequest class. */
@Getter
@Builder
public class OrderPaymentRequest {

    /** Payment amount. */
    private String amount;

    /** Payment currency. */
    private String currency;

    /** Payment method information. */
    private OrderPaymentMethodRequest paymentMethod;
}
