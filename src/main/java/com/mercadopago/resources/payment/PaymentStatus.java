package com.mercadopago.resources.payment;

/**
 * Constants representing all possible statuses of a MercadoPago payment.
 *
 * <p>Use these constants to compare against the {@code status} field of a {@link Payment}
 * resource instead of hard-coding string values. Each constant corresponds to a stage
 * in the payment lifecycle.
 *
 * @see Payment#getStatus()
 */
public class PaymentStatus {
  /** The payment has been approved and the funds have been accredited to the collector. */
  public static final String APPROVED = "approved";

  /** The user has not yet completed the payment process (e.g. waiting for bank transfer or ticket payment). */
  public static final String PENDING = "pending";

  /** The payment has been authorized by the card issuer but the funds have not yet been captured. */
  public static final String AUTHORIZED = "authorized";

  /** The payment is being reviewed by MercadoPago’s fraud prevention system. */
  public static final String IN_PROCESS = "in_process";

  /** A dispute has been initiated by the buyer and the payment is under mediation. */
  public static final String IN_MEDIATION = "in_mediation";

  /** The payment was rejected by the payment processor or issuer. The buyer may retry. */
  public static final String REJECTED = "rejected";

  /** The payment was cancelled by one of the parties or because the expiration time elapsed. */
  public static final String CANCELLED = "cancelled";

  /** The payment was fully refunded to the buyer. */
  public static final String REFUNDED = "refunded";

  /** A chargeback was initiated on the buyer’s credit card by the issuing bank. */
  public static final String CHARGED_BACK = "charged_back";
}
