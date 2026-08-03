package com.mercadopago.resources.preapprovalplan;

import java.math.BigDecimal;
import lombok.Getter;

/**
 * Recurring billing configuration of a subscription plan.
 *
 * @see PreApprovalPlan
 */
@Getter
public class PreApprovalPlanAutoRecurring {

  /** Billing frequency unit (e.g., days, months). */
  private String frequencyType;

  /** Number of frequency units between each billing cycle. */
  private Integer frequency;

  /** ISO 4217 currency code for the recurring charge. */
  private String currencyId;

  /** Amount charged to the subscriber on each billing cycle. */
  private BigDecimal transactionAmount;

  /** Maximum number of billing cycles; {@code null} for indefinite. */
  private Integer repetitions;
}
