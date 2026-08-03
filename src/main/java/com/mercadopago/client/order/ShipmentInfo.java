package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

/**
 * Shipment information within the additional info section of an order. Provides flags
 * indicating the type of delivery chosen by the buyer, such as express shipping or
 * local pickup at the seller's location.
 */
@Getter
@Builder
public class ShipmentInfo {
    /** Whether express shipping was selected for this order. */
    private final Boolean express;

    /** Whether the buyer will pick up the order locally at the seller's location. */
    private final Boolean localPickup;
}
