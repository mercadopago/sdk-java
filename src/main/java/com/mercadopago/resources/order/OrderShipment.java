package com.mercadopago.resources.order;

import lombok.Getter;

/**
 * Resource representing shipment information associated with a MercadoPago Order.
 * Contains the delivery address, package dimensions, and shipping preferences
 * such as express delivery or seller pickup.
 */
@Getter
public class OrderShipment {

    /** Delivery address where the shipment will be received. */
    private OrderReceiverAddress receiverAddress;

    /** Width of the package in the seller's configured unit of measurement. */
    private float width;

    /** Height of the package in the seller's configured unit of measurement. */
    private float height;

    /** Whether the shipment uses express delivery. */
    private Boolean expressShipment;

    /** Whether the buyer will pick up the order at the seller's location. */
    private Boolean pickUpOnSeller;
}
