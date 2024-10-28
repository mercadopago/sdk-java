package com.mercadopago.resources.order;

import lombok.Getter;

/** OrderCategoryDescriptor class. */
@Getter
public class OrderCategoryDescriptor {
    /** Event date. */
    private String eventDate;

    /** Passenger information. */
    private OrderPassenger passenger;

    /** Route information. */
    private OrderRoute route;
}
