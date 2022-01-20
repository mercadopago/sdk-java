package com.mercadopago.client.payment;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;

/** PaymentRouteRequest class. */
@Getter
@Builder
public class PaymentRouteRequest {
  /** Departure. */
  private final String departure;

  /** Destination. */
  private final String destination;

  /** Departure date time. */
  private final Date departureDateTime;

  /** Arrival date time. */
  private final Date arrivalDateTime;

  /** Company. */
  private final String company;
}
