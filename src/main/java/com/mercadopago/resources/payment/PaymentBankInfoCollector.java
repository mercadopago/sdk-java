package com.mercadopago.resources.payment;

import lombok.Data;

/** PaymentBankInfoCollector class. */
@Data
public class PaymentBankInfoCollector {
  private Long accountId;

  private String longName;
}
