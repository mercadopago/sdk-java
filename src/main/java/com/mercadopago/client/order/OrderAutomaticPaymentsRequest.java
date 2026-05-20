package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

// API version: 1ff4822a-2dfd-4393-800e-a562edb3fe32

/**
 * Request object for configuring automatic recurring payments within an order. Defines the
 * payment profile, retry policy, and scheduling dates for automated charge processing.
 */
@Getter
@Builder
public class OrderAutomaticPaymentsRequest {

  /** Identifier of the payment profile used for automatic charges. */
  private String paymentProfileId;

  /** Number of retry attempts if the automatic payment fails. */
  private Integer retries;

  /** Scheduled date for the automatic payment, in ISO 8601 format. */
  private String scheduleDate;

  /** Due date for the payment, in ISO 8601 format. */
  private String dueDate;
}
