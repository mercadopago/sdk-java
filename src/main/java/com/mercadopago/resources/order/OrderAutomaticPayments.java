package com.mercadopago.resources.order;

import lombok.Builder;
import lombok.Getter;

// API version: 1ff4822a-2dfd-4393-800e-a562edb3fe32

/** OrderAutomaticPayments class. */
@Getter
@Builder
public class OrderAutomaticPayments {

    /** Payment profile ID. */
    private String paymentProfileId;

    /** Retries. */
    private Integer retries;

    /** Schedule date. */
    private String scheduleDate;

    /** Due date. */
    private String dueDate;
}