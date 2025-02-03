package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

// API version: b950ae02-4f49-4686-9ad3-7929b21b6495

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
