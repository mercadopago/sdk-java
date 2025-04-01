package com.mercadopago.resources.order;

import lombok.Builder;
import lombok.Getter;

// API version: 1ff4822a-2dfd-4393-800e-a562edb3fe32

/** OrderStoredCredential class. */
@Getter
@Builder
public class OrderStoredCredential {

    /** Payment Initiator. */
    private String paymentInitiator;

    /** Reason. */
    private String reason;

    /** Store Payment Method. */
    private Boolean storePaymentMethod;

    /** First Payment. */
    private Boolean firstPayment;
}
