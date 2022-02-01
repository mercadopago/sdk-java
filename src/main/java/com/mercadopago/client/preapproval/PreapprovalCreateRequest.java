package com.mercadopago.client.preapproval;

import lombok.Builder;
import lombok.Getter;

/** Data to create a Preapproval. */
@Getter
@Builder
public class PreapprovalCreateRequest {
  /** Payer email. */
  private final String payerEmail;

  /** Return URL. */
  private final String backUrl;

  /** Seller ID. */
  private final String collectorId;

  /** Preapproval title. */
  private final String reason;

  /** Preapproval reference value. */
  private final String externalReference;

  /** Preapproval status. */
  private final String status;

  /** Recurring data. */
  private final PreApprovalAutoRecurringCreateRequest autoRecurring;
}
