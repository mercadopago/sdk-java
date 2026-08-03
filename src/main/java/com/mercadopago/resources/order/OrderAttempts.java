package com.mercadopago.resources.order;

import com.mercadopago.resources.paymentmethod.PaymentMethod;
import lombok.Getter;

// API version: d0494f1c-8d81-4c76-ae1d-0c65bb8ef6de

/**
 * Resource representing a payment attempt within a MercadoPago Order payment.
 * Records the outcome of an individual attempt to process a payment, including
 * its status and the payment method used.
 */
@Getter
public class OrderAttempts {

    /** Unique identifier of this payment attempt. */
    private String id;

    /** Status of this payment attempt (e.g., "approved", "rejected"). */
    private String status;

    /** Detailed sub-status providing the reason for the attempt outcome. */
    private String statusDetail;

    /** Payment method used in this attempt. */
    private PaymentMethod paymentMethod;

}
