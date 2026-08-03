package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

// API version: 1ff4822a-2dfd-4393-800e-a562edb3fe32
/**
 * Configuration for payment method restrictions and defaults within an order. Controls which
 * payment methods are allowed, sets default selections, and defines installment limits and
 * cost absorption rules for the checkout experience.
 */
@Builder
@Getter
public class OrderPaymentMethodConfig {

    /** List of payment method IDs that are not allowed for this order. */
    private List<String> notAllowedIds;

    /** List of payment method types that are not allowed for this order. */
    private List<String> notAllowedTypes;

    /** Default payment method ID pre-selected in the checkout. */
    private String defaultId;

    /** Maximum number of installments allowed for this order. */
    private Integer maxInstallments;

    /** Default number of installments pre-selected in the checkout. */
    private Integer defaultInstallments;

    /** Default payment method type pre-selected in the checkout. */
    private String defaultType;

    /** Who absorbs the installment cost ("buyer" or "seller"). */
    private String installmentsCost;

    /** Installment configuration including interest-free rules. */
    private OrderInstallmentsRequest installments;

    /** Minimum number of installments allowed for this order. */
    private Integer minInstallments;
}
