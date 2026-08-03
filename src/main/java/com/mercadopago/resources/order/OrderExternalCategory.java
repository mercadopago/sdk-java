package com.mercadopago.resources.order;

import lombok.Getter;

/**
 * Resource representing an external category classification for a MercadoPago Order item.
 * Used to map items to category taxonomies defined outside of MercadoPago.
 */
@Getter
public class OrderExternalCategory {

    /** Identifier of the external category in the integrator's classification system. */
    private String id;
}
