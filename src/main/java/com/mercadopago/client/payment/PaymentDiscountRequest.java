package com.mercadopago.client.payment;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

/**
 * Request object representing a discount rule applied to a payment.
 * Defines the type, value, and validity date of the discount, typically
 * used for early-payment incentives on boleto or invoice payments.
 */
@Getter
@Builder
public class PaymentDiscountRequest {

  /** Discount type (e.g. "fixed" or "percentage"). */
  private String type;

  /** Discount value, interpreted as a fixed amount or percentage depending on type. */
  private BigDecimal value;

  /** Last date on which the discount is valid. */
  private LocalDate limitDate;
}
