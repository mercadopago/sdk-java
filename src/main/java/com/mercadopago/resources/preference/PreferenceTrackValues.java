package com.mercadopago.resources.preference;

import lombok.Getter;

/** Values of tracks to be executed during the users interaction in the Checkout flow. */
@Getter
public class PreferenceTrackValues {
  /** conversion_id for GTM Google Ads Conversion Tracking tag. */
  private String conversionId;

  /** conversion_label for GTM Google Ads Conversion Tracking tag. */
  private String conversionLabel;

  /** pixel_id for Facebook Pixel. */
  private String pixelId;
}
