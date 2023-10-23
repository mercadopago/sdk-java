package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentTransactionDataRequest {
  private boolean firstTimeUse;
  private PaymentSubscriptionSequenceRequest subscriptionSequence;
  private String subscriptionId;
  private PaymentInvoicePeriodRequest invoicePeriod;
  private PaymentPaymentReferenceRequest paymentReference;
  private String billingDate;
}
