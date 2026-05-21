package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

/**
 * Platform-level information within the additional info section of an order. Groups data about
 * the marketplace or platform that originated the transaction, including shipment logistics,
 * seller profile, and authentication context.
 */
@Getter
@Builder
public class PlatformInfo {
    /** Shipment details managed by the platform (delivery promise, tracking, etc.). */
    private final PlatformShipment shipment;

    /** Information about the seller on the platform. */
    private final SellerInfo seller;

    /** Authentication method or token used by the platform. */
    private final String authentication;
}
