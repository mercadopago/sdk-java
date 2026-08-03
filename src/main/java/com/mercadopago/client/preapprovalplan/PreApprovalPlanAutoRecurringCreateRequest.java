package com.mercadopago.client.preapprovalplan;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

/**
 * Auto-recurring billing configuration for creating a subscription plan.
 *
 * @see PreApprovalPlanCreateRequest
 */
@Getter
@Builder
public class PreApprovalPlanAutoRecurringCreateRequest {

  /** Billing frequency unit (e.g., days, months). */
  private final String frequencyType;

  /** Number of frequency units between each billing cycle. */
  private final Integer frequency;

  /** ISO 4217 currency code for the recurring charge. */
  private final String currencyId;

  /** Amount charged to the subscriber on each billing cycle. */
  private final BigDecimal transactionAmount;

  /** Maximum number of billing cycles; {@code null} for indefinite. */
  private final Integer repetitions;
}
