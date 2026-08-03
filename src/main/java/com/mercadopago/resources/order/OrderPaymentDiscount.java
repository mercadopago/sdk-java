package com.mercadopago.resources.order;

import lombok.Getter;

/**
 * Resource representing a discount applied to a specific payment within a MercadoPago Order.
 * Indicates the type of discount that was applied to reduce the payment amount.
 */
@Getter
public class OrderPaymentDiscount {

    /** Type of discount applied to the payment (e.g., "campaign", "coupon"). */
    private String type;
}
