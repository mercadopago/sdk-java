package com.mercadopago.resources.payment;

import lombok.Getter;

/** PaymentBankInfo class. */
@Getter
public class PaymentBankInfo {
  /** Payer info. */
  private PaymentBankInfoPayer payer;

  /** Collector info. */
  private PaymentBankInfoCollector collector;

  /** Is same bank account owner. */
  private String isSameBankAccountOwner;
}
