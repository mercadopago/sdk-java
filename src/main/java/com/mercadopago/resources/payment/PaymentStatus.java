package com.mercadopago.resources.payment;

/** PaymentStatus class. */
public class PaymentStatus {
  /** The payment has been approved and accredited. */
  public static final String APPROVED = "approved";

  /** The user has not yet completed the payment process. */
  public static final String PENDING = "pending";

  /** The payment has been authorized but not captured yet. */
  public static final String AUTHORIZED = "authorized";

  /** Payment is being reviewed. */
  public static final String IN_PROCESS = "in_process";

  /** Users have initiated a dispute. */
  public static final String IN_MEDIATION = "in_mediation";

  /** Payment was rejected. The user may retry payment. */
  public static final String REJECTED = "rejected";

  /** Payment was cancelled by one of the parties or because time for payment has expired. */
  public static final String CANCELLED = "cancelled";

  /** Payment was refunded to the user. */
  public static final String REFUNDED = "refunded";

  /** Was made a chargeback in the buyer’s credit card. */
  public static final String CHARGED_BACK = "charged_back";
}
