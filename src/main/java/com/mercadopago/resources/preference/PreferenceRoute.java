package com.mercadopago.resources.preference;

import java.util.Date;
import lombok.Getter;

/** Flight information. */
@Getter
public class PreferenceRoute {
  /** Departure. */
  private String departure;

  /** Destination. */
  private String destination;

  /** Departure date. */
  private Date departureDateTime;

  /** Arrival date. */
  private Date arrivalDateTime;

  /** Company. */
  private String company;
}
