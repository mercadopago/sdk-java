package com.mercadopago.client.preference;

import lombok.Builder;
import lombok.Getter;

/** Track to be executed during the users interaction in the Checkout flow. */
@Getter
@Builder
public class PreferenceTrackRequest {
  /** Track type (google_ad or facebook_ad). */
  private final String type;

  /** Values according the track type. */
  private final PreferenceTrackValuesRequest values;
}
