package com.mercadopago.resources.order;

import lombok.Getter;

/**
 * Resource representing a tax applied to a MercadoPago Order.
 * Contains the tax type, rate or amount, and the fiscal condition of the payer
 * that determines the applicable tax.
 */
@Getter
public class OrderTax {

    /** Fiscal condition of the payer that determines the tax applicability. */
    private String payerCondition;

    /** Type of tax applied (e.g., "IVA", "IIBB"). */
    private String type;

    /** Tax value expressed as a decimal string representing a rate or fixed amount. */
    private String value;
}
