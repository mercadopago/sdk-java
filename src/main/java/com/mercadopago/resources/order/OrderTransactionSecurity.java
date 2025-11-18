package com.mercadopago.resources.order;

import lombok.Getter;

/**
 * Transaction Security response for 3DS authentication.
 * Contains information about 3DS validation and challenge URL when required.
 */
@Getter
public class OrderTransactionSecurity {

    /**
     * Challenge URL for 3DS authentication.
     * This URL should be displayed in an iframe when challenge is required.
     * Present only when status is "action_required" and status_detail is "pending_challenge".
     */
    private String url;

    /**
     * Validation mode used for 3DS authentication.
     * Values: "always", "on_fraud_risk", "never"
     */
    private String validation;

    /**
     * Liability shift configuration.
     * Values: "required", "preferred"
     */
    private String liabilityShift;
}
