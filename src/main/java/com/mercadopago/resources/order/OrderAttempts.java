package com.mercadopago.resources.order;

import com.mercadopago.resources.paymentmethod.PaymentMethod;
import lombok.Getter;

// API version: d0494f1c-8d81-4c76-ae1d-0c65bb8ef6de

/** Attempts class. */
@Getter
public class OrderAttempts {

    /** Attempts id. */
    private String id;

    /** Attempts status. */
    private String status;

    /** Attempts status detail. */
    private String statusDetail;

    /** Attempts Payment Method. */
    private PaymentMethod paymentMethod;

}
