package com.mercadopago.resources.payment;

import java.math.BigInteger;
import lombok.Getter;

/**
 * Resource that holds the bank account information of the payer in a MercadoPago payment.
 *
 * <p>Identifies the payer's bank account used to send funds via bank transfer or Pix.
 * Includes the email associated with the account for notification purposes.
 *
 * @see PaymentBankInfo#getPayer()
 */
@Getter
public class PaymentBankInfoPayer {
  /** Email address associated with the payer's bank account. */
  private String email;

  /** Unique identifier of the payer's bank account. */
  private BigInteger accountId;

  /** Full display name of the payer's bank account. */
  private String longName;
}
