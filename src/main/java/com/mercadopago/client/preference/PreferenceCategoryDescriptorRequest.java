package com.mercadopago.client.preference;

import lombok.Builder;
import lombok.Getter;

/** Item information related to the category. */
@Getter
@Builder
public class PreferenceCategoryDescriptorRequest {
  /** Passenger information. */
  private final PreferencePassengerRequest passenger;

  /** Flight information. */
  private final PreferenceRouteRequest route;
}
