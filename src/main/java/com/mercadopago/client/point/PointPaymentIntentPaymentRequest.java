package com.mercadopago.client.point;

import lombok.Builder;
import lombok.Getter;

/**
 * Payment configuration for a Point payment intent, defining the payment type, installments and
 * cost allocation.
 *
 * @see com.mercadopago.client.point.PointPaymentIntentRequest
 */
@Getter
@Builder
public class PointPaymentIntentPaymentRequest {
  /** Number of installments (1-72). Only applicable when type is {@code credit_card}. */
  private final Integer installments;

  /** Payment type: {@code credit_card}, {@code debit_card}, or {@code voucher_card}. */
  private final String type;

  /** Who pays the installment cost ({@code seller} or {@code buyer}). Only for credit cards. */
  private final String installmentsCost;

  /** Voucher brand ({@code sodexo} or {@code alelo}). Only when type is {@code voucher_card}. */
  private final String voucherType;
}
