package com.mercadopago.resources.payment;

import lombok.Getter;

/** PaymentBankInfoPayer class. */
@Getter
public class PaymentBankInfoPayer {
  private String email;

  private Long accountId;

  private String longName;
}
