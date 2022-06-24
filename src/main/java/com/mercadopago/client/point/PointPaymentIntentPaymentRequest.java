package com.mercadopago.client.point;

import lombok.Builder;
import lombok.Getter;

/** Properties of the payment intent. */
@Getter
@Builder
public class PointPaymentIntentPaymentRequest {
  /**
   * A value that must be between 1 and 72, this value can be set only if type field is equal to
   * credit_card.
   */
  private final Integer installments;

  /** An alphanumeric value that must be credit_card, debit_card or voucher_card. */
  private final String type;

  /**
   * A string value that must be one of this (seller or buyer), this value can be set only if type
   * field is equal to credit_card.
   */
  private final String installmentsCost;

  /**
   * A string value that must be one of this (sodexo or alelo), this value can be set only if type
   * field is equal to voucher_card.
   */
  private final String voucherType;
}
