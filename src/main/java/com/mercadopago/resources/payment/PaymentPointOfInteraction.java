package com.mercadopago.resources.payment;

import lombok.Getter;

/** PaymentPointOfInteraction class. */
@Getter
public class PaymentPointOfInteraction {
  private String type;

  private String subType;

  private PaymentApplicationData applicationData;

  private PaymentTransactionData transactionData;
}
