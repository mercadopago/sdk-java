package com.mercadopago.client.order;

import lombok.Getter;

/** OrderShipmentRequest class. */
@Getter
public class OrderShipmentRequest {

    /** Receiver address information. */
    private OrderReceiverAddressRequest receiverAddress;

    /** Weight of the package. */
    private float width;

    /** Height of the package. */
    private float height;

    /** If the shipment is express. */
    private Boolean expressShipment;

    /** If to pick up on seller. */
    private Boolean pickUpOnSeller;
}
