package com.mercadopago.client.preapproval;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

/** Recurring data. */
@Getter
@Builder
public class PreApprovalAutoRecurringCreateRequest {
  /** Currency ID. */
  private final String currencyId;

  /** Recurring amount. */
  private final BigDecimal transactionAmount;

  /** Recurring frequency. */
  private final Integer frequency;

  /** Recurring frequency type (days or months). */
  private final String frequencyType;

  /** Recurring start date. Must follow the patter ("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") */
  private final String startDate;

  /** Recurring end date. Must follow the patter ("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") */
  private final String endDate;
}
