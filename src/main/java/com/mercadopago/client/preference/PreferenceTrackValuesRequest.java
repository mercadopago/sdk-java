package com.mercadopago.client.preference;

import lombok.Builder;
import lombok.Getter;

/** Values of tracks to be executed during the users interaction in the Checkout flow. */
@Getter
@Builder
public class PreferenceTrackValuesRequest {
  /** conversion_id for GTM Google Ads Conversion Tracking tag. */
  private final String conversionId;

  /** conversion_label for GTM Google Ads Conversion Tracking tag. */
  private final String conversionLabel;

  /** pixel_id for Facebook Pixel. */
  private final String pixelId;
}
