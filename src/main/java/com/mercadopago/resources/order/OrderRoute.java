package com.mercadopago.resources.order;

import lombok.Getter;

/**
 * Resource representing travel route information for a MercadoPago Order item.
 * Used in travel-related transactions to describe the itinerary, including
 * departure and arrival locations, times, and the carrier company.
 */
@Getter
public class OrderRoute {
    /** Name of the departure city or airport code. */
    private String departure;

    /** Name of the destination city or airport code. */
    private String destination;

    /** ISO 8601 date-time of the scheduled departure. */
    private String departureDateTime;

    /** ISO 8601 date-time of the scheduled arrival. */
    private String arrivalDateTime;

    /** Name of the transport company or airline operating this route. */
    private String company;
}
