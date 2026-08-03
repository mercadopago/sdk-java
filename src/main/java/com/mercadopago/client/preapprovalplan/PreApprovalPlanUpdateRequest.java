package com.mercadopago.client.preapprovalplan;

import lombok.Builder;
import lombok.Getter;

/**
 * Request to update an existing subscription plan.
 *
 * @see com.mercadopago.client.preapprovalplan.PreApprovalPlanClient
 */
@Getter
@Builder
public class PreApprovalPlanUpdateRequest {

  /** New title for the plan. */
  private final String reason;

  /** New back URL for the plan. */
  private final String backUrl;

  /** New status (e.g., cancelled). */
  private final String status;

  /** Updated recurring billing configuration. */
  private final PreApprovalPlanAutoRecurringUpdateRequest autoRecurring;
}
