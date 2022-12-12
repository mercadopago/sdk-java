package com.mercadopago.resources.payment;

import java.math.BigInteger;
import lombok.Getter;

/** PaymentBankInfoPayer class. */
@Getter
public class PaymentBankInfoPayer {
  /** Email. */
  private String email;

  /** Account ID. */
  private BigInteger accountId;

  /** Account long name. */
  private String longName;
}
