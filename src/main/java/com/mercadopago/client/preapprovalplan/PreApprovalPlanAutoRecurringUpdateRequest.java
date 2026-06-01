package com.mercadopago.client.preapprovalplan;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

/**
 * Auto-recurring billing configuration for updating a subscription plan.
 *
 * @see PreApprovalPlanUpdateRequest
 */
@Getter
@Builder
public class PreApprovalPlanAutoRecurringUpdateRequest {

  /** Updated billing frequency unit. */
  private final String frequencyType;

  /** Updated number of frequency units. */
  private final Integer frequency;

  /** Updated ISO 4217 currency code. */
  private final String currencyId;

  /** Updated charge amount. */
  private final BigDecimal transactionAmount;

  /** Updated maximum number of billing cycles. */
  private final Integer repetitions;
}
