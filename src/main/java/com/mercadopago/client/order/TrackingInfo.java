package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

/**
 * Tracking information for a platform-managed shipment. Contains the tracking code and
 * current delivery status, used within {@link PlatformShipment} to monitor package logistics.
 */
@Getter
@Builder
public class TrackingInfo {
    /** Carrier-provided tracking code for the shipment. */
    private final String code;

    /** Current status of the tracked shipment (e.g. "in_transit", "delivered"). */
    private final String status;
}


