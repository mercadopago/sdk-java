package com.mercadopago.resources.preference;

import java.time.OffsetDateTime;
import lombok.Getter;

/**
 * Resource representing flight route information for travel-related preference items.
 *
 * <p>Contains departure and arrival details for a flight segment, including airport codes,
 * scheduled times, and the operating airline. Used within the category descriptor of travel items.
 *
 * @see PreferenceCategoryDescriptor
 */
@Getter
public class PreferenceRoute {
  /** Departure airport code or city name. */
  private String departure;

  /** Destination airport code or city name. */
  private String destination;

  /** Scheduled departure date and time. */
  private OffsetDateTime departureDateTime;

  /** Scheduled arrival date and time. */
  private OffsetDateTime arrivalDateTime;

  /** Name of the airline or transportation company. */
  private String company;
}
