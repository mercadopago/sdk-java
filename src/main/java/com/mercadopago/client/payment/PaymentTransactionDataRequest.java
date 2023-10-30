package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentTransactionDataRequest {
  /** First time use. */
  private boolean firstTimeUse;
  /**  Subscription sequence. */
  private PaymentSubscriptionSequenceRequest subscriptionSequence;
  /** Subscription id. */
  private String subscriptionId;
  /**  Invoice period. */
  private PaymentInvoicePeriodRequest invoicePeriod;
  /** Payment reference. */
  private PaymentPaymentReferenceRequest paymentReference;
  /** Billing date. */
  private String billingDate;
}
