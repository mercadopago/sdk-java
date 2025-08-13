package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

/**
 * Additional information for the order (request-only fields).
 */
@Getter
@Builder
public class AdditionalInfoRequest {
    /** Information about the payer. */
    private final PayerInfo payer;

    /** Information about the shipment. */
    private final ShipmentInfo shipment;

    /** Platform information. */
    private final PlatformInfo platform;

    /** Travel information. */
    private final TravelInfo travel;
}

