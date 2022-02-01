package com.mercadopago.client.preapproval;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;

/** Recurring data. */
@Getter
@Builder
public class PreApprovalAutoRecurringUpdateRequest {
  /** Recurring amount. */
  private final BigDecimal transactionAmount;

  /** Recurring start date. */
  private final Date startDate;

  /** Recurring end date. */
  private final Date endDate;
}
