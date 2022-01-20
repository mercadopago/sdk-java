package com.mercadopago.resources.payment;

import lombok.Getter;

/** PaymentBankInfoPayer class. */
@Getter
public class PaymentBankInfoPayer {
  /** Email. */
  private String email;

  /** Account ID. */
  private Long accountId;

  /** Account long name. */
  private String longName;
}
