package com.mercadopago.resources.order;

import lombok.Getter;

/** OrderShipment class. */
@Getter
public class OrderShipment {

    /** Receiver address information. */
    private OrderReceiverAddress receiverAddress;

    /** Weight of the package. */
    private float width;

    /** Height of the package. */
    private float height;

    /** If the shipment is express. */
    private Boolean expressShipment;

    /** If to pick up on seller. */
    private Boolean pickUpOnSeller;
}
