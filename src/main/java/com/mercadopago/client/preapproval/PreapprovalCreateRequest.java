package com.mercadopago.client.preapproval;

import lombok.Builder;
import lombok.Getter;

/**
 * Request object to create a new preapproval (subscription) in MercadoPago. Contains payer
 * details, seller information, and auto-recurring billing configuration.
 *
 * @see com.mercadopago.client.preapproval.PreApprovalAutoRecurringCreateRequest
 * @see com.mercadopago.client.preapproval.PreapprovalClient
 */
@Getter
@Builder
public class PreapprovalCreateRequest {
  /** Email address of the payer (subscriber). */
  private final String payerEmail;

  /** URL to redirect the user after the preapproval flow. */
  private final String backUrl;

  /** Unique identifier of the seller (collector). */
  private final String collectorId;

  /** Title or reason describing the subscription. */
  private final String reason;

  /** External reference to synchronize with your system. */
  private final String externalReference;

  /** Initial status of the preapproval. */
  private final String status;

  /** Auto-recurring billing configuration for the subscription. */
  private final PreApprovalAutoRecurringCreateRequest autoRecurring;
}
