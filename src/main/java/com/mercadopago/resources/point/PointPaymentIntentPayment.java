package com.mercadopago.resources.point;

import lombok.Getter;

/**
 * Resource containing payment configuration details for a Point payment intent.
 *
 * <p>Defines how the payment should be processed, including the payment method type (credit card,
 * debit card, or voucher), installment settings, and who bears the installment cost.
 *
 * @see PointPaymentIntent
 */
@Getter
public class PointPaymentIntentPayment {
  /** Unique identifier of the resulting payment, available after processing. */
  private String id;

  /**
   * Number of installments (1 to 72). Only applicable when {@code type} is {@code credit_card}.
   */
  private Integer installments;

  /**
   * Party responsible for the installment cost ({@code seller} or {@code buyer}). Only applicable
   * when {@code type} is {@code credit_card}.
   */
  private String installmentsCost;

  /** Payment method type: {@code credit_card}, {@code debit_card}, or {@code voucher_card}. */
  private String type;

  /** Specific voucher type when payment type is {@code voucher_card}. */
  private String voucherType;
}
