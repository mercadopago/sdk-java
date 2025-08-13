package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

/**
 * Platform information for additional info.
 */
@Getter
@Builder
public class PlatformInfo {
    /** Shipment details. */
    private final PlatformShipment shipment;

    /** Seller information. */
    private final SellerInfo seller;

    /** Authentication information. */
    private final String authentication;
}
