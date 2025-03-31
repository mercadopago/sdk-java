package com.mercadopago.resources.order;

import lombok.Getter;

/** OrderIntegrationData class. */
@Getter
public class OrderIntegrationData {

    /** Identifier of the Mercado Pago application that created the Order. */
    private String  applicationId;
}
