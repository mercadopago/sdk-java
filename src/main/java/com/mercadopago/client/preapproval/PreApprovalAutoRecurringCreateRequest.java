package com.mercadopago.client.preapproval;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Getter;

/**
 * Auto-recurring billing configuration used when creating a preapproval (subscription). Defines
 * the currency, amount, frequency, and date range for recurring charges.
 *
 * @see com.mercadopago.client.preapproval.PreapprovalCreateRequest
 */
@Getter
@Builder
public class PreApprovalAutoRecurringCreateRequest {
  /** Currency identifier (e.g., ARS, BRL, MXN). */
  private final String currencyId;

  /** Amount charged on each recurring cycle. */
  private final BigDecimal transactionAmount;

  /** Number of time units between each charge. */
  private final Integer frequency;

  /** Time unit for the frequency ({@code days} or {@code months}). */
  private final String frequencyType;

  /** Date when the recurring billing starts. */
  private final OffsetDateTime startDate;

  /** Date when the recurring billing ends. */
  private final OffsetDateTime endDate;
}
