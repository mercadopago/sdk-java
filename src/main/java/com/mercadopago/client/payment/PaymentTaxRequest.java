package com.mercadopago.client.payment;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

/**
 * Request object representing a tax applied to a payment.
 * Each tax has a type, a monetary value, and a flag indicating
 * whether the value represents a percentage or a fixed amount.
 */
@Getter
@Builder
public class PaymentTaxRequest {
  /** Tax type identifier (e.g. "IVA", "IPI"). */
  private final String type;

  /** Tax value, either a fixed amount or a percentage depending on the {@code percentage} flag. */
  private final BigDecimal value;

  /** Whether the value represents a percentage ({@code true}) or a fixed amount ({@code false}). */
  private final Boolean percentage;
}
