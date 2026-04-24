package com.mercadopago.resources.order;

import java.util.List;
import lombok.Getter;

/**
 * Resource representing interest-free installment options for a MercadoPago Order payment method.
 * Specifies how interest-free installments are determined and the allowed installment counts.
 */
@Getter
public class OrderInstallmentsInterestFree {

    /** Type of interest-free rule (e.g., "specific_values"). */
    private String type;

    /** List of installment counts offered without interest (e.g., [3, 6, 12]). */
    private List<Integer> values;
}
