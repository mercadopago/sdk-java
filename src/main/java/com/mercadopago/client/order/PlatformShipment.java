package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

/**
 * Platform shipment information (additional fields grouped under platform.shipment).
 */
@Getter
@Builder
public class PlatformShipment {
    /** Delivery promise. */
    private final String deliveryPromise;

    /** Drop shipping. */
    private final String dropShipping;

    /** Safety. */
    private final String safety;

    /** Tracking information. */
    private final TrackingInfo tracking;

    /** Withdrawn. */
    private final Boolean withdrawn;
}


