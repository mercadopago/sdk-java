package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

/**
 * Payer information inside additional_info.
 */
@Getter
@Builder
public class PayerInfo {
    /** Payer authentication type. */
    private final String authenticationType;

    /** Payer registration date. */
    private final String registrationDate;

    /** If payer is prime user. */
    private final Boolean isPrimeUser;

    /** If is first online purchase. */
    private final Boolean isFirstPurchaseOnline;

    /** Date of last purchase. */
    private final String lastPurchase;
}


