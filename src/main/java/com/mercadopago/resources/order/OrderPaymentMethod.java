package com.mercadopago.resources.order;

import lombok.Getter;

/** OrderPaymentMethod class. */
@Getter
public class OrderPaymentMethod {
    /** Payment method ID. */
    private String id;

    /** Payment method type. */
    private String type;

    /** Payment method token. */
    private String token;

    /** Number of installments. */
    private int installments;

    /** Payment method issuer. */
    private String issuerId;

    /** How will look the payment in the card bill (e.g.: MERCADOPAGO).  */
    private String StatementDescriptor;
}
