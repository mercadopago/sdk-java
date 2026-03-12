package com.mercadopago.resources.order;

import lombok.Getter;

/** OrderTax class. */
@Getter
public class OrderTax {

    /** Payer condition. */
    private String payerCondition;

    /** Type. */
    private String type;

    /** Value. */
    private String value;
}
