package com.mercadopago.client.preference;

import lombok.Builder;
import lombok.Getter;

/**
 * Tracking parameter values for advertising conversion tags used during the checkout flow.
 * Supports Google Ads and Facebook Pixel identifiers.
 *
 * @see com.mercadopago.client.preference.PreferenceTrackRequest
 */
@Getter
@Builder
public class PreferenceTrackValuesRequest {
  /** Conversion ID for Google Ads (GTM) conversion tracking. */
  private final String conversionId;

  /** Conversion label for Google Ads (GTM) conversion tracking. */
  private final String conversionLabel;

  /** Pixel ID for Facebook Pixel tracking. */
  private final String pixelId;
}
