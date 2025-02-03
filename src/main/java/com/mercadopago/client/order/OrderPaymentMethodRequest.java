package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

// API version: b950ae02-4f49-4686-9ad3-7929b21b6495

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
