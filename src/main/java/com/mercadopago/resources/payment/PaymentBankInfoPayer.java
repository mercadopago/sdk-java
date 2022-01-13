package com.mercadopago.resources.payment;

import lombok.Data;

/** PaymentBankInfoPayer class. */
@Data
public class PaymentBankInfoPayer {
  private String email;

  private Long accountId;

  private String longName;
}
