package com.mercadopago.client.order;

import lombok.Getter;

/**
 * Request object representing a travel route leg within an order. Contains origin and
 * destination information along with travel times and the carrier company, used for
 * airline and travel industry payment processing.
 */
@Getter
public class OrderRouteRequest {

    /** Departure city or airport code. */
    private String departure;

    /** Destination city or airport code. */
    private String destination;

    /** Departure date and time in ISO 8601 format. */
    private String departureDateTime;

    /** Arrival date and time in ISO 8601 format. */
    private String arrivalDateTime;

    /** Name of the carrier or airline company. */
    private String company;
}
