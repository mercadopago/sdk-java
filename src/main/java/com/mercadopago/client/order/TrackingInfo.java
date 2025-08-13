package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

/**
 * Tracking information for platform.shipment.
 */
@Getter
@Builder
public class TrackingInfo {
    /** Tracking code. */
    private final String code;

    /** Tracking status. */
    private final String status;
}


