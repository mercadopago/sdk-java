package com.mercadopago.resources.order;

import lombok.Builder;
import lombok.Getter;

// API version: 1ff4822a-2dfd-4393-800e-a562edb3fe32

/** OrderInvoicePeriod class. */
@Getter
@Builder
public class OrderInvoicePeriod {

    /** Type. */
    private String type;

    /** Period. */
    private Integer period;

}
