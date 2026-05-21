package com.mercadopago.resources.payment;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;

/**
 * Resource that represents a discount applied to a MercadoPago payment method.
 *
 * <p>Defines the type of discount, its monetary value, and an optional limit date
 * after which the discount is no longer applicable.
 *
 * @see PaymentRules#getDiscounts()
 */
@Getter
public class PaymentDiscount {

  /** Type of discount (e.g. fixed, percentage). */
  private String type;

  /** Monetary value of the discount. */
  private BigDecimal value;

  /** Date after which this discount is no longer valid. */
  private LocalDate limitDate;
}
