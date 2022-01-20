package com.mercadopago.resources.payment;

import lombok.Getter;

/** PaymentTransactionData class. */
@Getter
public class PaymentTransactionData {
  private String qrCode;

  private String qrCodeBase64;

  private String transactionId;

  private Long bankTransferId;

  private Long financialInstitution;

  private PaymentBankInfo bankInfo;
}
