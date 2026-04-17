package com.mercadopago.resources.order;

import lombok.Getter;

/** OrderDiscountPaymentMethod class. */
@Getter
public class OrderDiscountPaymentMethod {

    /** Type. */
    private String type;

    /** New total amount. */
    private String newTotalAmount;
}
