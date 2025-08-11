package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

/**
 * Shipment information for additional info.
 */
@Getter
@Builder
public class ShipmentInfo {
    /** Indicates if it's express shipping. */
    private final Boolean express;

    /** Indicates if it's local pickup. */
    private final Boolean localPickup;
}
