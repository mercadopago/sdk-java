package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

// API version: 1ff4822a-2dfd-4393-800e-a562edb3fe32

/** OrderAutomaticPaymentsRequest class. */
@Getter
@Builder
public class OrderAutomaticPaymentsRequest {

  /** Amount. */
  private String amount;

  /** Payment Profile ID. */
  private OrderPaymentMethodRequest paymentMethod;

  /** Retries. */
  private OrderAutomaticPayments automaticPayments;

  /** Schedule Date. */
  private OrderStoredCredentialRequest scheduleDate;

  /** Due Date. */
  private OrderSubscriptionDataRequest dueDate;
}
