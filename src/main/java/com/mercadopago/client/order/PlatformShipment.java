package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

/**
 * Shipment information managed by the platform, provided within the additional info section.
 * Contains logistics details such as delivery promises, drop-shipping flags, safety indicators,
 * and package tracking data.
 */
@Getter
@Builder
public class PlatformShipment {
    /** Expected delivery promise or estimated delivery date. */
    private final String deliveryPromise;

    /** Drop-shipping indicator or configuration. */
    private final String dropShipping;

    /** Safety level or indicator for the shipment. */
    private final String safety;

    /** Tracking information including code and status. */
    private final TrackingInfo tracking;

    /** Whether the shipment has been withdrawn by the buyer. */
    private final Boolean withdrawn;
}


