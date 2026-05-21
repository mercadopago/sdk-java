package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

// API version: 1ff4822a-2dfd-4393-800e-a562edb3fe32

/**
 * Request object containing subscription data for recurring payment orders. Provides billing
 * context such as sequence information, invoice details, and billing dates required for
 * subscription-based payment processing.
 */
@Getter
@Builder
public class OrderSubscriptionDataRequest {

  /** Sequence information indicating the position of this payment within the subscription. */
  private OrderSubscriptionSequenceRequest subscriptionSequence;

  /** Identifier of the invoice associated with this subscription payment. */
  private String invoiceId;

  /** Period covered by this invoice (type and duration). */
  private OrderInvoicePeriodRequest invoicePeriod;

  /** Date when the billing occurs, in ISO 8601 format. */
  private String billingDate;

}
