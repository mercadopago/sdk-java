package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/**
 * Request object containing transaction data associated with the point of interaction.
 * Carries subscription and recurring-payment details such as sequence, billing
 * period, and payment references.
 */
@Getter
@Builder
public class PaymentTransactionDataRequest {
  /** Whether this is the first use of the payment credential for a subscription. */
  private boolean firstTimeUse;
  /** Subscription sequence tracking the current and total number of payments. */
  private PaymentSubscriptionSequenceRequest subscriptionSequence;
  /** Unique identifier of the subscription this payment belongs to. */
  private String subscriptionId;
  /** Invoice period defining the billing cycle type and duration. */
  private PaymentInvoicePeriodRequest invoicePeriod;
  /** Reference to a previous payment in the subscription series. */
  private PaymentPaymentReferenceRequest paymentReference;
  /** Billing date for the current invoice period (e.g. "2023-01-15"). */
  private String billingDate;
}
