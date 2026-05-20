package com.mercadopago.resources.order;

import lombok.Getter;

/**
 * Resource representing 3D Secure (3DS) transaction security configuration and response
 * for a MercadoPago Order payment. Contains the authentication mode, challenge URL
 * for cardholder verification, and liability shift preference.
 */
@Getter
public class OrderTransactionSecurity {

    /** Unique identifier of this transaction security entry. */
    private String id;

    /**
     * Challenge URL for 3DS cardholder authentication.
     * Should be displayed in an iframe when status is "action_required"
     * and status_detail is "pending_challenge".
     */
    private String url;

    /**
     * Validation mode controlling when 3DS authentication is triggered.
     * Possible values: "always", "on_fraud_risk", "never".
     */
    private String validation;

    /**
     * Liability shift preference indicating whether 3DS authentication
     * is mandatory or best-effort. Possible values: "required", "preferred".
     */
    private String liabilityShift;
}
