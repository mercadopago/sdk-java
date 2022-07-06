package com.mercadopago.resources.payment;

import lombok.Getter;

/** PaymentPointOfInteraction class. */
@Getter
public class PaymentPointOfInteraction {
  /** Type. */
  private String type;

  /** Sub type. */
  private String subType;

  /** Linked to information. */
  private String linkedTo;

  /** Application data. */
  private PaymentApplicationData applicationData;

  /** Transaction data. */
  private PaymentTransactionData transactionData;
}
