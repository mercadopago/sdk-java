package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

/**
 * Request object representing a single route (leg) in a travel itinerary. Contains departure
 * and destination cities, travel times, and the carrier company, used for airline and travel
 * industry fraud prevention.
 */
@Getter
@Builder
public class TravelRouteRequest {
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