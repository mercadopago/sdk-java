package com.mercadopago.resources.payment;

import java.math.BigInteger;
import lombok.Getter;

/**
 * Resource that holds the bank account information of the collector (seller) in a MercadoPago payment.
 *
 * <p>Identifies the collector's bank account used to receive funds from bank transfer or
 * Pix-based payments.
 *
 * @see PaymentBankInfo#getCollector()
 */
@Getter
public class PaymentBankInfoCollector {
  /** Unique identifier of the collector's bank account. */
  private BigInteger accountId;

  /** Full display name of the collector's bank account. */
  private String longName;
}
