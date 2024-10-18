package com.mercadopago.client.order;

import lombok.Getter;

/** OrderCategoryDescriptorRequest class. */
@Getter
public class OrderCategoryDescriptorRequest {

    /** Event date. */
    private String eventDate;

    /** Passenger information. */
    private OrderPassengerRequest passenger;

    /** Route information. */
    private OrderRouteRequest route;
}

