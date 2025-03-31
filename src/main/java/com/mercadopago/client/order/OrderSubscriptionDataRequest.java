package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

// API version: 1ff4822a-2dfd-4393-800e-a562edb3fe32

/** OrderSubscriptionDataRequest class. */
@Getter
@Builder
public class OrderSubscriptionDataRequest {

  /** Subscription Sequence. */
  private OrderSubscriptionSequenceRequest subscriptionSequence;

  /** Invoice ID. */
  private String invoiceId;

  /** Invoice Period. */
  private OrderInvoicePeriodRequest invoicePeriod;

  /** Billing Date. */
  private String billingDate;

}
