package com.mercadopago.resources.preference;

import com.mercadopago.client.preference.PreferenceTrackValuesRequest;
import lombok.Getter;

/**
 * Resource representing a tracking tag executed during the buyer's checkout interaction.
 *
 * <p>Enables conversion tracking by associating the checkout flow with advertising platforms
 * such as Google Ads or Facebook Ads. Each track specifies a type and its corresponding
 * platform-specific identifiers.
 *
 * @see Preference
 * @see PreferenceTrackValues
 */
@Getter
public class PreferenceTrack {
  /** Tracking platform type ({@code google_ad} or {@code facebook_ad}). */
  private String type;

  /** Platform-specific tracking values (conversion IDs, pixel IDs, etc.). */
  private PreferenceTrackValuesRequest values;
}
