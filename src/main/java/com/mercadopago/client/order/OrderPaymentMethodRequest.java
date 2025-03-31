package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

// API version: 1ff4822a-2dfd-4393-800e-a562edb3fe32

/** OrderPaymentMethodCreateRequest class. */
@Getter
@Builder
public class OrderPaymentMethodRequest {

    /** Payment method ID. */
    private String id;

    /** Payment method type. */
    private String type;

    /** Payment method token. */
    private String token;

    /** Number of installments. */
    private int installments;

    /** How will look the payment in the card bill (e.g.: MERCADOPAGO).  */
    private String statementDescriptor;
}
