package com.mercadopago.client.preference;

import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Getter;

/**
 * Flight or route information associated with a travel-related preference item, including origin,
 * destination, schedule, and carrier details.
 *
 * @see com.mercadopago.client.preference.PreferenceCategoryDescriptorRequest
 */
@Getter
@Builder
public class PreferenceRouteRequest {
  /** Departure location (airport code or city). */
  private final String departure;

  /** Destination location (airport code or city). */
  private final String destination;

  /** Scheduled departure date and time. */
  private final OffsetDateTime departureDateTime;

  /** Scheduled arrival date and time. */
  private final OffsetDateTime arrivalDateTime;

  /** Name of the airline or transport company. */
  private final String company;
}
