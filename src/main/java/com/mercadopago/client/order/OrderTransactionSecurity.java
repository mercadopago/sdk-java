package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

/**
 * Transaction Security configuration for 3DS authentication.
 * Used in Order requests to configure 3D Secure validation.
 */
@Getter
@Builder
public class OrderTransactionSecurity {

    /**
     * Validation mode for 3DS authentication.
     * Possible values:
     * - "always": Always require 3DS authentication
     * - "on_fraud_risk": Require 3DS based on fraud risk assessment (recommended)
     * - "never": Never require 3DS authentication (default)
     */
    private String validation;

    /**
     * Liability shift configuration.
     * Possible values:
     * - "required": Financial liability in case of chargeback is with the card brand
     * - "preferred": Preferred liability shift
     */
    private String liabilityShift;
}
