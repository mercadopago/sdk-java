package com.mercadopago.resources.order;

import lombok.Getter;

// API version: 1ff4822a-2dfd-4393-800e-a562edb3fe32
/**
 * Resource representing the Point-of-Sale (POS) terminal configuration for a MercadoPago Order.
 * Defines how the order is presented and processed on a physical MercadoPago Point device.
 */
@Getter
public class OrderPointConfig {

    /** Identifier of the MercadoPago Point terminal device assigned to this order. */
    private String terminalId;

    /** Whether to print a receipt on the terminal after payment completion. */
    private String printOnTerminal;

    /** Duration the order is displayed on the terminal screen, in ISO 8601 duration format. */
    private String screenTime;
}
