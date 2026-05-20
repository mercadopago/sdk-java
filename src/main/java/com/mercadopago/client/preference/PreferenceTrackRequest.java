package com.mercadopago.client.preference;

import lombok.Builder;
import lombok.Getter;

/**
 * Advertising or analytics tracking tag to execute during the buyer's interaction in the
 * checkout flow.
 *
 * @see com.mercadopago.client.preference.PreferenceRequest
 * @see com.mercadopago.client.preference.PreferenceTrackValuesRequest
 */
@Getter
@Builder
public class PreferenceTrackRequest {
  /** Track type ({@code google_ad} or {@code facebook_ad}). */
  private final String type;

  /** Tracking parameter values specific to the track type. */
  private final PreferenceTrackValuesRequest values;
}
