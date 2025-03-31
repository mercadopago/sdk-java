package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

// API version: 1ff4822a-2dfd-4393-800e-a562edb3fe32
/** OrderPaymentMethodConfig class. */
@Builder
@Getter
public class OrderPaymentMethodConfig {

    /** Not allowed IDs. */
    private List<String> notAllowedIds;

    /** Not allowed types. */
    private List<String> notAllowedTypes;

    /** Default ID. */
    private String defaultId;

    /** Max installments. */
    private Integer maxInstallments;

    /** Default installments. */
    private Integer defaultInstallments;
}
