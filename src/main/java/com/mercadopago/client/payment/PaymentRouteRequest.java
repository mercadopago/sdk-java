package com.mercadopago.client.payment;

import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Getter;

/**
 * Request object describing a travel route associated with a purchased item.
 * Used within the category descriptor to provide flight or trip details
 * that support fraud-prevention analysis for travel-related transactions.
 */
@Getter
@Builder
public class PaymentRouteRequest {
  /** Departure location (e.g. airport IATA code or city name). */
  private final String departure;

  /** Destination location (e.g. airport IATA code or city name). */
  private final String destination;

  /** Scheduled departure date and time. */
  private final OffsetDateTime departureDateTime;

  /** Scheduled arrival date and time. */
  private final OffsetDateTime arrivalDateTime;

  /** Name of the transportation company or airline. */
  private final String company;
}
