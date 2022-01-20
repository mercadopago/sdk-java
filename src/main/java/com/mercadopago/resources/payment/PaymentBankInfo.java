package com.mercadopago.resources.payment;

import lombok.Getter;

/** PaymentBankInfo class. */
@Getter
public class PaymentBankInfo {
  private PaymentBankInfoPayer payer;

  private PaymentBankInfoCollector collector;

  private String isSameBankAccountOwner;
}
