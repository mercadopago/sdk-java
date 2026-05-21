package com.mercadopago.client.order;

import lombok.Getter;

/**
 * Request object containing shipment details for an order. Includes the receiver's address,
 * package dimensions, and shipping options such as express delivery and seller pickup.
 */
@Getter
public class OrderShipmentRequest {

    /** Destination address where the shipment will be delivered. */
    private OrderReceiverAddressRequest receiverAddress;

    /** Width of the package in centimeters. */
    private float width;

    /** Height of the package in centimeters. */
    private float height;

    /** Whether the shipment uses express delivery. */
    private Boolean expressShipment;

    /** Whether the buyer will pick up the order at the seller's location. */
    private Boolean pickUpOnSeller;
}
