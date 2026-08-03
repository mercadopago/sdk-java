package com.mercadopago.client.order;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

/**
 * Travel information within the additional info section of an order. Used for airline and travel
 * industry transactions to provide passenger and route details that improve fraud detection
 * and chargeback management.
 */
@Getter
@Builder
public class TravelInfo {
    /** List of passengers traveling on this itinerary. */
    private final List<TravelPassengerRequest> passengers;

    /** List of travel routes (legs) in the itinerary. */
    private final List<TravelRouteRequest> routes;
}
