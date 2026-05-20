package com.mercadopago.resources.order;

import lombok.Getter;

// API version: d0494f1c-8d81-4c76-ae1d-0c65bb8ef6de

/**
 * Resource representing the payer associated with a MercadoPago Order.
 * Contains identifying information about the person or entity making the payment.
 */
@Getter
public class OrderPayer {

    /** MercadoPago customer identifier for the payer. */
    private String customerId;

    /** Entity type of the payer (e.g., "individual", "association"). */
    private String entityType;
}
