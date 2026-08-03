package com.mercadopago.client.preapprovalplan;

import lombok.Builder;
import lombok.Getter;

/**
 * Request to create a new subscription plan.
 *
 * @see com.mercadopago.client.preapprovalplan.PreApprovalPlanClient
 */
@Getter
@Builder
public class PreApprovalPlanCreateRequest {

  /** Short descriptive title of the plan visible to subscribers during checkout. */
  private final String reason;

  /** URL to redirect the subscriber after completing the checkout flow. */
  private final String backUrl;

  /** Integrator-provided external reference for reconciliation. */
  private final String externalReference;

  /** Recurring billing configuration (frequency, currency, amount). */
  private final PreApprovalPlanAutoRecurringCreateRequest autoRecurring;
}
