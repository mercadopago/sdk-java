package com.mercadopago.resources.payment;

import java.util.List;
import lombok.Getter;

/**
 * Resource that defines the financial rules applied to a MercadoPago payment method.
 *
 * <p>Contains discount, fine, and interest configurations that affect the final payment
 * amount. These rules are typically set by the collector and applied at payment creation.
 *
 * @see PaymentData#getRules()
 */
@Getter
public class PaymentRules {

  /** List of discounts applicable to the payment (e.g. early payment discounts). */
  private List<PaymentDiscount> discounts;

  /** Fine charged for late payments. */
  private PaymentFee fine;

  /** Interest charged for deferred or late payments. */
  private PaymentFee interest;
}
