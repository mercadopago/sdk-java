package com.mercadopago.resources.order;

import lombok.Getter;

/**
 * Resource representing integration metadata for a MercadoPago Order.
 * Contains identifiers used to correlate the order with the integrator's
 * MercadoPago application.
 */
@Getter
public class OrderIntegrationData {

    /** Identifier of the MercadoPago application that created this order. */
    private String  applicationId;
}
