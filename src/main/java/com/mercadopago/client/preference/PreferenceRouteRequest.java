package com.mercadopago.client.preference;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;

/** Flight information. */
@Getter
@Builder
public class PreferenceRouteRequest {
  /** Departure. */
  private final String departure;

  /** Destination. */
  private final String destination;

  /** Departure date. */
  private final Date departureDateTime;

  /** Arrival date. */
  private final Date arrivalDateTime;

  /** Company. */
  private final String company;
}
