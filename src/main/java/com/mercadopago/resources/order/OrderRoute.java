package com.mercadopago.resources.order;

import lombok.Getter;

/** OrderRoute class. */
@Getter
public class OrderRoute {
    /** Departure city. */
    private String departure;

    /** Destination city. */
    private String destination;

    /** Departure date and time. */
    private String departureDateTime;

    /** Arrival date and time. */
    private String arrivalDateTime;

    /** Company name. */
    private String company;
}
