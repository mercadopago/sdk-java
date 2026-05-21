package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

/**
 * Request object for differential pricing configuration in an order. Allows the seller to
 * offer different payment conditions (e.g. installment plans, discounts) to specific buyer
 * segments by referencing a pre-configured differential pricing ID.
 */
@Getter
@Builder
public class OrderDifferentialPricing {

    /** Identifier of the differential pricing configuration to apply. */
    private Integer id;
}
