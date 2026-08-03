package com.mercadopago.client.preapproval;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Getter;

/**
 * Auto-recurring billing configuration used when updating an existing preapproval (subscription).
 * Allows modification of the amount and date range for recurring charges.
 *
 * @see com.mercadopago.client.preapproval.PreapprovalUpdateRequest
 */
@Getter
@Builder
public class PreApprovalAutoRecurringUpdateRequest {
  /** Updated amount charged on each recurring cycle. */
  private final BigDecimal transactionAmount;

  /** Updated date when the recurring billing starts. */
  private final OffsetDateTime startDate;

  /** Updated date when the recurring billing ends. */
  private final OffsetDateTime endDate;
}
