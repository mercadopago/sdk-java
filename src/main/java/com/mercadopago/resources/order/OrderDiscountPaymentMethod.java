package com.mercadopago.resources.order;

import lombok.Getter;

/**
 * Resource representing a discount tied to a specific payment method type in a MercadoPago Order.
 * Indicates the adjusted total amount the payer would pay when using this payment method.
 */
@Getter
public class OrderDiscountPaymentMethod {

    /** Payment method type that qualifies for this discount (e.g., "credit_card", "debit_card"). */
    private String type;

    /** Adjusted total order amount after applying the discount, expressed as a decimal string. */
    private String newTotalAmount;
}
