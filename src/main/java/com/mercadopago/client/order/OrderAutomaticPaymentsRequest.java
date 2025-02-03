package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

// API version: b950ae02-4f49-4686-9ad3-7929b21b6495

/** OrderAutomaticPaymentsRequest class. */
@Getter
@Builder
public class OrderAutomaticPaymentsRequest {

  /** Payment Profile ID. */
  private String paymentProfileId;

  /** Retries. */
  private Integer retries;

  /** Schedule Date. */
  private String scheduleDate;

  /** Due Date. */
  private String dueDate;
}
