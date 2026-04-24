package com.mercadopago.client.preapproval;

import lombok.Builder;
import lombok.Getter;

/**
 * Request object to update an existing preapproval (subscription) in MercadoPago. Allows
 * modification of the return URL, reason, status, and recurring billing configuration.
 *
 * @see com.mercadopago.client.preapproval.PreApprovalAutoRecurringUpdateRequest
 * @see com.mercadopago.client.preapproval.PreapprovalClient
 */
@Getter
@Builder
public class PreapprovalUpdateRequest {
  /** Updated URL to redirect the user after the preapproval flow. */
  private final String backUrl;

  /** Updated title or reason describing the subscription. */
  private final String reason;

  /** Updated external reference to synchronize with your system. */
  private final String externalReference;

  /** Updated status of the preapproval. */
  private final String status;

  /** Updated auto-recurring billing configuration for the subscription. */
  private final PreApprovalAutoRecurringUpdateRequest autoRecurring;
}
