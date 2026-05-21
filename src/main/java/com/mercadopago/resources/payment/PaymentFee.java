package com.mercadopago.resources.payment;

import java.math.BigDecimal;
import lombok.Getter;

/**
 * Resource that represents a fee or financial charge rule applied to a MercadoPago payment.
 *
 * <p>Used within {@link PaymentRules} to define fines and interest charges associated
 * with late or deferred payments.
 *
 * @see PaymentRules#getFine()
 * @see PaymentRules#getInterest()
 */
@Getter
public class PaymentFee {

  /** Type of the fee (e.g. percentage, fixed). */
  private String type;

  /** Monetary or percentage value of the fee. */
  private BigDecimal value;
}
