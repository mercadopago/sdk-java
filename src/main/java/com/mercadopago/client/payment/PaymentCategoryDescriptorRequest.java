package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/**
 * Request object providing category-specific descriptors for a purchased item.
 * Primarily used for travel-related items to include passenger and route
 * (flight) information that aids in fraud-prevention analysis.
 */
@Getter
@Builder
public class PaymentCategoryDescriptorRequest {
  /** Passenger details associated with the purchased item (e.g. airline ticket). */
  private final PaymentPassengerRequest passenger;

  /** Route or flight details associated with the purchased item. */
  private final PaymentRouteRequest route;
}
