package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

/**
 * Configuration for 3D Secure (3DS) transaction security in an order. Controls whether 3DS
 * authentication is required and how liability shift is handled in case of chargebacks.
 * Used within {@link OrderOnlineConfig} to enforce security policies on online payments.
 */
@Getter
@Builder
public class OrderTransactionSecurity {

    /** Validation mode for 3DS authentication ("always", "on_fraud_risk", or "never"). */
    private String validation;

    /** Liability shift policy for chargebacks ("required" or "preferred"). */
    private String liabilityShift;
}
