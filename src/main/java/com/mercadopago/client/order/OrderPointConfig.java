package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

// API version: 1ff4822a-2dfd-4393-800e-a562edb3fe32
/**
 * Configuration for Point-of-Sale (POS) terminal integration within an order. Controls terminal
 * selection, receipt printing behavior, and display timeout for in-person payment scenarios.
 */
@Getter
@Builder
public class OrderPointConfig {

    /** Identifier of the Point terminal device to process the payment. */
    private String terminalId;

    /** Whether to print a receipt on the terminal after payment. */
    private String printOnTerminal;

    /** Duration the payment screen is displayed on the terminal, in ISO 8601 duration format. */
    private String screenTime;
}
