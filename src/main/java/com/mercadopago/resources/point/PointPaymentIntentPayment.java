package com.mercadopago.resources.point;

import lombok.Getter;

/** Properties of the payment intent. */
@Getter
public class PointPaymentIntentPayment {
  /** Payment ID. */
  private String id;

  /**
   * An integer value that must be between 1 and 72, this value can be set only if type field is
   * equal to credit_card.
   */
  private Integer installments;

  /**
   * A string value that must be one of this (seller or buyer), this value can be set only if type
   * field is equal to credit_card.
   */
  private String installmentsCost;

  /** An alphanumeric value that must be credit_card, debit_card or voucher_card. */
  private String type;

  /** Voucher type. */
  private String voucherType;
}
