package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

// API version: 1ff4822a-2dfd-4393-800e-a562edb3fe32
/** OrderPointConfig class. */
@Getter
@Builder
public class OrderPointConfig {

    /** Terminal ID for Point integration. Pattern: ^(GERTEC_MP35P|INGENICO_MOVE2500|PAX_A910|NEWLAND_N950)__[a-zA-Z0-9]+$ */
    private String terminalId;

    /** Print on terminal option. Values: seller_ticket, no_ticket. Default: seller_ticket */
    private String printOnTerminal;

    /** Ticket number (1-20 characters). */
    private String ticketNumber;

    /** Screen time in ISO 8601 duration format (e.g. 'PT30S' for 30 seconds). */
    private String screenTime;

    /** Congratulations time in ISO 8601 duration format (e.g. 'PT30S' for 30 seconds). */
    private String congratsTime;
}
