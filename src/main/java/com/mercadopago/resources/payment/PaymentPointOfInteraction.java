package com.mercadopago.resources.payment;

import lombok.Getter;

/** PaymentPointOfInteraction class. */
@Getter
public class PaymentPointOfInteraction {
  /** Type. */
  private String type;

  /** Sub type. */
  private String subType;

  /** Application data. */
  private PaymentApplicationData applicationData;

  /** Transaction data. */
  private PaymentTransactionData transactionData;
}
