package com.mercadopago.resources.payment;

import lombok.Getter;

/**
 * Resource that holds transaction-level data from the point of interaction for a MercadoPago payment.
 *
 * <p>Contains QR code information for Pix payments, bank transfer details, ticket URLs for
 * offline payment methods, subscription-related data for recurring payments, and
 * credential-on-file fields for CREDENTIAL_ON_FILE flows. This data is typically provided
 * within the {@link PaymentPointOfInteraction} resource.
 *
 * @see PaymentPointOfInteraction#getTransactionData()
 */
@Getter
public class PaymentTransactionData {
  /** QR code string content used for Pix or other QR-based payment methods. */
  private String qrCode;

  /** Base64-encoded image of the QR code for rendering in client applications. */
  private String qrCodeBase64;

  /** BACEN (Brazilian Central Bank) end-to-end identifier for Pix transactions. */
  private String transactionId;

  /** Identifier of the bank transfer transaction. */
  private Long bankTransferId;

  /** Identifier of the financial institution that processed the transaction. */
  private Long financialInstitution;

  /** Banking information for both the payer and the collector involved in the transaction. */
  private PaymentBankInfo bankInfo;

  /** URL of the payment ticket or voucher for offline payment methods. */
  private String ticketUrl;

  /** Whether this is the first time the payment method is being used by the payer. */
  private boolean firstTimeUse;

  /** Sequence information for subscription-based recurring payments. */
  private PaymentSubscriptionSequence subscriptionSequence;

  /** Identifier of the subscription associated with this recurring payment. */
  private String subscriptionId;

  /** Invoice period details for subscription or recurring billing payments. */
  private PaymentInvoicePeriod invoicePeriod;

  /** Reference to a related payment in the context of recurring or linked transactions. */
  private PaymentPaymentReference paymentReference;

  /** Billing date for subscription or recurring payment invoices. */
  private String billingDate;

  /** Legacy card-network transaction identifier within transaction data. */
  private String networkTransactionId;
  /** Card-network identifiers for this credential-on-file transaction. */
  private PaymentNetworkData networkData;

  /**
   * Whether this is the first transaction for a CREDENTIAL_ON_FILE payment.
   * Replaces the legacy {@code firstTimeUse} field in new integrations.
   */
  private boolean firstTransaction;

  /**
   * Storage mode for the credential in a CREDENTIAL_ON_FILE flow.
   * Possible values: {@code "store"} (storing a new credential) or
   * {@code "stored"} (using an already stored credential).
   */
  private String storage;

  /**
   * Indicates who initiated the transaction in a CREDENTIAL_ON_FILE flow.
   * Possible values: {@code "customer"} or {@code "merchant"}.
   */
  private String transactionInitiator;

  /**
   * Reference to the stored credential used in a CREDENTIAL_ON_FILE flow.
   * Contains the identifier of the credential being referenced.
   */
  private PaymentReference reference;
}
