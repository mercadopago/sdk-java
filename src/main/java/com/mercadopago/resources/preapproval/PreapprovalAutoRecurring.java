package com.mercadopago.resources.preapproval;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Getter;

/**
 * Resource representing the recurring billing configuration of a subscription (preapproval).
 *
 * <p>Defines how often and how much is charged to the payer on each billing cycle, including the
 * currency, amount, frequency, and the active time window of the recurring charges.
 *
 * @see Preapproval
 */
@Getter
public class PreapprovalAutoRecurring {
  /** ISO 4217 currency code for the recurring charges (e.g., ARS, BRL, USD). */
  private String currencyId;

  /** Amount charged on each billing cycle. */
  private BigDecimal transactionAmount;

  /** Number of time units between each billing cycle. */
  private Integer frequency;

  /** Unit of time for the billing frequency ({@code days} or {@code months}). */
  private String frequencyType;

  /** Date when the recurring charges begin. */
  private OffsetDateTime startDate;

  /** Date when the recurring charges end. */
  private OffsetDateTime endDate;
}
