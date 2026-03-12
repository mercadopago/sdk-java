package com.mercadopago.resources.order;

import java.util.List;
import lombok.Getter;

/** OrderDiscounts class. */
@Getter
public class OrderDiscounts {

    /** Payment methods. */
    private List<OrderDiscountPaymentMethod> paymentMethods;
}
