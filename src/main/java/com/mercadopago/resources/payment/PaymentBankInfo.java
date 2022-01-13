package com.mercadopago.resources.payment;

import lombok.Data;

/**
 * PaymentBankInfo class.
 */
@Data
public class PaymentBankInfo {
  private PaymentBankInfoPayer payer;

  private PaymentBankInfoCollector collector;

  private String isSameBankAccountOwner;
}
