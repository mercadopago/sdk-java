package com.mercadopago.client.preference;

import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Getter;

/**
 * Category-specific descriptor for a preference item, providing additional details such as
 * passenger data, route information, and event dates (e.g., for travel or event tickets).
 *
 * @see com.mercadopago.client.preference.PreferenceItemRequest
 */
@Getter
@Builder
public class PreferenceCategoryDescriptorRequest {
  /** Passenger details associated with the item. */
  private final PreferencePassengerRequest passenger;

  /** Route or flight details associated with the item. */
  private final PreferenceRouteRequest route;

  /** Date of the event related to the item. */
  private final OffsetDateTime eventDate;
}
