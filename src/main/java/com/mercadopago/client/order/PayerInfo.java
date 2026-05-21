package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

/**
 * Additional payer information provided within the {@code additional_info} section of an order.
 * Contains risk-assessment data such as authentication type, account age, and purchase history
 * to improve fraud detection and payment approval rates.
 */
@Getter
@Builder
public class PayerInfo {
    /** Type of authentication used by the payer (e.g. "Gmail", "Facebook", "native"). */
    private final String authenticationType;

    /** Date when the payer registered on the platform, in ISO 8601 format. */
    private final String registrationDate;

    /** Whether the payer is a prime or premium user. */
    private final Boolean isPrimeUser;

    /** Whether this is the payer's first online purchase. */
    private final Boolean isFirstPurchaseOnline;

    /** Date of the payer's last purchase, in ISO 8601 format. */
    private final String lastPurchase;

    /** Payer's IP address (required by risk engine for PSE in Colombia). */
    private final String ipAddress;
}


