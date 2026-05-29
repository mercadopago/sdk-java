package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

// API version: 1ff4822a-2dfd-4393-800e-a562edb3fe32

/**
 * Request object for stored credential (card-on-file) information in an order payment.
 * Used to indicate whether the payment uses a previously stored card and the context
 * of the transaction (merchant-initiated vs. cardholder-initiated), as required by
 * card network regulations.
 */
@Getter
@Builder
public class OrderStoredCredentialRequest {

  /** Who initiated the payment: "cardholder" or "merchant". */
  private String paymentInitiator;

  /** Reason for storing the credential (e.g. "recurring", "installment", "unscheduled"). */
  private String reason;

  /** Whether to store the payment method for future transactions. */
  private Boolean storePaymentMethod;

  /** Whether this is the first payment using this stored credential. */
  private Boolean firstPayment;

  /**
   * Reference to the previous transaction in a recurring series. Required from the second charge
   * onwards to link this payment to the original card-network authorization.
   * Type: String (transaction ID).
   */
  private String prevTransactionRef;
}
