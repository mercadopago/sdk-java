package com.mercadopago.client.payment;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

/**
 * Request object defining the financial rules applied to a payment, such as
 * early-payment discounts, late-payment fines, and interest charges.
 * Typically used with boleto or other deferred payment methods.
 */
@Getter
@Builder
public class PaymentRulesRequest {

  /** List of discounts applicable to the payment (e.g. early-payment discounts). */
  private List<PaymentDiscountRequest> discounts;

  /** Fine configuration applied when the payment is made after the due date. */
  private PaymentFeeRequest fine;

  /** Interest configuration applied when the payment is made after the due date. */
  private PaymentFeeRequest interest;
}
