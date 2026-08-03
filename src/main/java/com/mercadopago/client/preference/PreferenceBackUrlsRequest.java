package com.mercadopago.client.preference;

import lombok.Builder;
import lombok.Getter;

/**
 * Callback URLs that the buyer is redirected to after the checkout flow, depending on the payment
 * outcome.
 *
 * @see com.mercadopago.client.preference.PreferenceRequest
 */
@Getter
@Builder
public class PreferenceBackUrlsRequest {
  /** URL to redirect the buyer to when the payment succeeds. */
  private final String success;

  /** URL to redirect the buyer to when the payment is pending. */
  private final String pending;

  /** URL to redirect the buyer to when the payment fails. */
  private final String failure;
}
