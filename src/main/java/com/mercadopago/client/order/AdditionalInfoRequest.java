package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

/**
 * Request object containing supplementary information for an order. Provides extra context
 * about the payer, shipment, platform, and travel details to improve fraud prevention
 * and payment approval rates.
 */
@Getter
@Builder
public class AdditionalInfoRequest {
    /** Additional payer details such as authentication type and registration date. */
    private final PayerInfo payer;

    /** Additional shipment details such as express shipping and local pickup flags. */
    private final ShipmentInfo shipment;

    /** Platform-level information including seller data and shipment tracking. */
    private final PlatformInfo platform;

    /** Travel-related information including passenger and route details. */
    private final TravelInfo travel;
}

