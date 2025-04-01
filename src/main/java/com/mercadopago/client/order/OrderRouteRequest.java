package com.mercadopago.client.order;

import lombok.Getter;

/** OrderRouteRequest class. */
@Getter
public class OrderRouteRequest {

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
