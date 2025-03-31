package com.mercadopago.client.common;


import lombok.Builder;
import lombok.Getter;

/** InvoicePeriod class. */
@Getter
@Builder
public class InvoicePeriod {

    /** Invoice type. */
    private String type;

    /** Invoice  period. */
    private Integer period;
}
