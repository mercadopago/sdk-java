package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/**
 * Request object containing transaction data associated with the point of interaction.
 * Carries subscription, recurring-payment, and credential-on-file details such as
 * sequence, billing period, and payment references.
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
  /** Legacy card-network transaction identifier within transaction data. */
  private String networkTransactionId;
  /**
   * Whether this is the first transaction for a CREDENTIAL_ON_FILE payment.
   * Replaces the legacy {@code firstTimeUse} field in new integrations.
   */
  private boolean firstTransaction;
  /**
   * Storage mode for the credential in a CREDENTIAL_ON_FILE flow.
   * Accepted values: {@code "store"} (storing a new credential) or
   * {@code "stored"} (using an already stored credential).
   */
  private String storage;
  /**
   * Indicates who initiated the transaction in a CREDENTIAL_ON_FILE flow.
   * Accepted values: {@code "customer"} or {@code "merchant"}.
   */
  private String transactionInitiator;
  /**
   * Reference to the stored credential used in a CREDENTIAL_ON_FILE flow.
   * Contains the identifier of the credential being referenced.
   */
  private PaymentReferenceRequest reference;
}
