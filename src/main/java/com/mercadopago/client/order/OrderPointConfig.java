package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

// API version: 1ff4822a-2dfd-4393-800e-a562edb3fe32
/** OrderPointConfig class. */
@Getter
@Builder
public class OrderPointConfig {

    /** Terminal ID for Point integration.*/
    private String terminalId;

    /** Print on terminal option. */
    private String printOnTerminal;

    /** Screen time in ISO 8601 duration format*/
    private String screenTime;
}
