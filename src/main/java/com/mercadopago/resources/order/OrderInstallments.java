package com.mercadopago.resources.order;

import lombok.Getter;

/**
 * Resource representing installment configuration for a MercadoPago Order payment method.
 * Defines both interest-free installment options and the general availability rules
 * for installment plans.
 */
@Getter
public class OrderInstallments {

    /** Interest-free installment options offered to the payer. */
    private OrderInstallmentsInterestFree interestFree;

    /** Configuration defining which installment plans are available. */
    private OrderInstallmentsAvailable available;
}
