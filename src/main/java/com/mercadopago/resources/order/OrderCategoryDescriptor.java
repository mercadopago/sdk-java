package com.mercadopago.resources.order;

import lombok.Getter;

/**
 * Resource representing category-specific descriptors for a MercadoPago Order item.
 * Used for travel and event categories to provide additional details such as
 * event dates, passenger identity, and route information.
 */
@Getter
public class OrderCategoryDescriptor {
    /** ISO 8601 date of the event associated with this item. */
    private String eventDate;

    /** Passenger details for travel-related order items. */
    private OrderPassenger passenger;

    /** Route details including departure, destination, and travel dates. */
    private OrderRoute route;
}
