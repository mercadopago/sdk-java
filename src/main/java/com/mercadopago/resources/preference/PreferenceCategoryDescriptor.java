package com.mercadopago.resources.preference;

import java.time.OffsetDateTime;
import lombok.Getter;

/**
 * Resource representing category-specific descriptive information for a preference item.
 *
 * <p>Provides additional context for items in specialized categories such as travel. Includes
 * passenger details, route information for flights, and the date of the event or trip.
 *
 * @see PreferenceItem
 * @see PreferencePassenger
 * @see PreferenceRoute
 */
@Getter
public class PreferenceCategoryDescriptor {
  /** Passenger details associated with this item (e.g., for airline tickets). */
  private PreferencePassenger passenger;

  /** Flight route information including departure and destination. */
  private PreferenceRoute route;

  /** Date of the event or trip associated with this item. */
  private OffsetDateTime eventDate;
}
