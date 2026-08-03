package com.mercadopago.resources.order;

import lombok.Builder;
import lombok.Getter;

// API version: 1ff4822a-2dfd-4393-800e-a562edb3fe32

/**
 * Resource representing automatic (recurring) payment configuration for a MercadoPago Order.
 * Defines the payment profile, retry policy, and scheduling dates for automated billing cycles.
 */
@Getter
@Builder
public class OrderAutomaticPayments {

    /** Identifier of the payment profile used for automatic charges. */
    private String paymentProfileId;

    /** Number of retry attempts allowed if the automatic payment fails. */
    private Integer retries;

    /** ISO 8601 date-time when the automatic payment is scheduled to be processed. */
    private String scheduleDate;

    /** ISO 8601 date-time by which the automatic payment must be completed. */
    private String dueDate;
}