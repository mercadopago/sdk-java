package com.mercadopago.resources.payment;

import com.mercadopago.client.payment.PaymentSubscriptionSequenceRequest;
import lombok.Getter;

/** PaymentTransactionData class. */
@Getter
public class PaymentTransactionData {
  /** QR code. */
  private String qrCode;

  /** QR code image in Base 64. */
  private String qrCodeBase64;

  /** Transaction ID. */
  private String transactionId;

  /** Bank transfer ID. */
  private Long bankTransferId;

  /** Financial institution. */
  private Long financialInstitution;

  /** Bank info. */
  private PaymentBankInfo bankInfo;

  /** Ticket Url. */
  private String ticketUrl;

  /** First time use. */
  private boolean firstTimeUse;

  /** Subscription sequence. */
  private PaymentSubscriptionSequence subscriptionSequence;

  /** Subscription id. */
  private String subscriptionId;

  /** Invoice period. */
  private PaymentInvoicePeriod invoicePeriod;

  /** Payment reference. */
  private PaymentPaymentReference paymentReference;

  /** Billing date. */
  private String billingDate;
}
