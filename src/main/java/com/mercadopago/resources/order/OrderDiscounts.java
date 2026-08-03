package com.mercadopago.resources.order;

import java.util.List;
import lombok.Getter;

/**
 * Resource representing discount information applied to a MercadoPago Order.
 * Contains discounts organized by payment method, each specifying the adjusted
 * total amount when that payment method is selected.
 */
@Getter
public class OrderDiscounts {

    /** List of payment-method-specific discounts available for this order. */
    private List<OrderDiscountPaymentMethod> paymentMethods;
}
