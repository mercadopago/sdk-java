package com.mercadopago.client.order;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

/**
 * Travel information.
 */
@Getter
@Builder
public class TravelInfo {
    /** List of passengers. */
    private final List<TravelPassengerRequest> passengers;

    /** List of routes. */
    private final List<TravelRouteRequest> routes;
}
