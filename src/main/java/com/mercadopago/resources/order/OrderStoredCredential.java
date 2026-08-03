package com.mercadopago.resources.order;

import lombok.Builder;
import lombok.Getter;

// API version: 1ff4822a-2dfd-4393-800e-a562edb3fe32

/**
 * Resource representing stored credential (card-on-file) information for a MercadoPago Order payment.
 * Used for recurring or merchant-initiated transactions where the payer's card data
 * has been previously stored with consent.
 */
@Getter
@Builder
public class OrderStoredCredential {

    /** Entity that initiated the payment: "cardholder" or "merchant". */
    private String paymentInitiator;

    /** Reason for using stored credentials (e.g., "recurring", "installment", "unscheduled"). */
    private String reason;

    /** Whether to store the payment method for future transactions. */
    private Boolean storePaymentMethod;

    /** Whether this is the first payment in a series using stored credentials. */
    private Boolean firstPayment;
}
