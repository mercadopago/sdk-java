package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * Request object containing shipment details for an order.
 * Optional; used when the product involves physical shipping logistics.
 */
@Getter
@Builder
public class OrderShipmentRequest {

    /**
     * Shipping mode. "custom": seller-defined shipping. "not_specified": no specification.
     */
    private String mode;

    /** Whether the buyer can pick up the product in person. When true, disables shipping cost. */
    private Boolean localPickup;

    /** Shipping cost when mode is "custom". Must be >= 0. */
    private String cost;

    /** When true, shipping is free for the buyer. Cannot be combined with cost > 0. */
    private Boolean freeShipping;

    /** List of free shipping method IDs available to the buyer. */
    private List<OrderFreeShippingMethodRequest> freeMethods;

    /** Delivery address for the shipment. */
    private OrderReceiverAddressRequest address;

    /** Destination address where the shipment will be delivered (legacy field). */
    private OrderReceiverAddressRequest receiverAddress;

    /** Width of the package in centimeters. */
    private Float width;

    /** Height of the package in centimeters. */
    private Float height;

    /** Whether the shipment uses express delivery. */
    private Boolean expressShipment;

    /** Whether the buyer will pick up the order at the seller's location (legacy field). */
    private Boolean pickUpOnSeller;
}
