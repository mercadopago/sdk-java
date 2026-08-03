package com.mercadopago.resources.order;

import lombok.Getter;

/**
 * Resource representing differential pricing configuration for a MercadoPago Order.
 * Allows applying different prices to buyers based on predefined pricing groups
 * configured in the seller's MercadoPago account.
 */
@Getter
public class OrderDifferentialPricing {

    /** Identifier of the differential pricing group to apply. */
    private Integer id;
}
