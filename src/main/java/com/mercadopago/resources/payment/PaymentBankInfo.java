package com.mercadopago.resources.payment;

import lombok.Getter;

/**
 * Resource that holds banking information for both parties involved in a MercadoPago payment.
 *
 * <p>Contains the bank account details of the payer and the collector (seller), and indicates
 * whether both accounts belong to the same owner. Used primarily for bank transfer and
 * Pix payment methods.
 *
 * @see PaymentTransactionData#getBankInfo()
 */
@Getter
public class PaymentBankInfo {
  /** Bank account information of the payer. */
  private PaymentBankInfoPayer payer;

  /** Bank account information of the collector (seller). */
  private PaymentBankInfoCollector collector;

  /** Indicates whether the payer and collector share the same bank account owner. */
  private String isSameBankAccountOwner;
}
