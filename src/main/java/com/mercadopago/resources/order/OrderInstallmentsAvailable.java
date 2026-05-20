package com.mercadopago.resources.order;

import lombok.Getter;

/**
 * Resource representing the availability configuration for installment plans
 * on a MercadoPago Order payment method.
 */
@Getter
public class OrderInstallmentsAvailable {

    /** Type of installment availability rule (e.g., "all", "specific"). */
    private String type;
}
