package com.mercadopago.resources.payment;

import lombok.Getter;

/** PaymentBankInfoCollector class. */
@Getter
public class PaymentBankInfoCollector {
  /** Account ID. */
  private Long accountId;

  /** Account long name. */
  private String longName;
}
