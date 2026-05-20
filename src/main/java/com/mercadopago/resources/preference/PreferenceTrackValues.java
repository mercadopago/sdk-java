package com.mercadopago.resources.preference;

import lombok.Getter;

/**
 * Resource containing platform-specific tracking identifiers for checkout conversion tracking.
 *
 * <p>Holds the values needed by advertising platforms to register conversions during the checkout
 * flow. Supports Google Ads (via conversion ID and label) and Facebook Ads (via pixel ID).
 *
 * @see PreferenceTrack
 */
@Getter
public class PreferenceTrackValues {
  /** Google Ads conversion ID used in the GTM Conversion Tracking tag. */
  private String conversionId;

  /** Google Ads conversion label used in the GTM Conversion Tracking tag. */
  private String conversionLabel;

  /** Facebook Pixel ID for tracking conversions on Facebook Ads. */
  private String pixelId;
}
