package com.mercadopago.client.preapproval;

import lombok.Builder;
import lombok.Getter;

/** Data to update a Preapproval. */
@Getter
@Builder
public class PreapprovalUpdateRequest {
  /** Return URL. */
  private final String backUrl;

  /** Preapproval title. */
  private final String reason;

  /** Preapproval reference value. */
  private final String externalReference;

  /** Preapproval status. */
  private final String status;

  /** Recurring data. */
  private final PreApprovalAutoRecurringUpdateRequest autoRecurring;
}
