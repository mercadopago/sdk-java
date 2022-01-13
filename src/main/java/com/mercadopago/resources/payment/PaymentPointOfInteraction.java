package com.mercadopago.resources.payment;

import lombok.Data;

/** PaymentPointOfInteraction class. */
@Data
public class PaymentPointOfInteraction {
  private String type;

  private String subType;

  private PaymentApplicationData applicationData;

  private PaymentTransactionData transactionData;
}
