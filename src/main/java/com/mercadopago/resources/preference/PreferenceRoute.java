package com.mercadopago.resources.preference;

import java.time.OffsetDateTime;
import lombok.Getter;

/** Flight information. */
@Getter
public class PreferenceRoute {
  /** Departure. */
  private String departure;

  /** Destination. */
  private String destination;

  /** Departure date. */
  private OffsetDateTime departureDateTime;

  /** Arrival date. */
  private OffsetDateTime arrivalDateTime;

  /** Company. */
  private String company;
}
