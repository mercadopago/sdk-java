package com.mercadopago.resources.payment;

import java.math.BigInteger;
import lombok.Getter;

/** PaymentBankInfoCollector class. */
@Getter
public class PaymentBankInfoCollector {
  /** Account ID. */
  private BigInteger accountId;

  /** Account long name. */
  private String longName;
}
