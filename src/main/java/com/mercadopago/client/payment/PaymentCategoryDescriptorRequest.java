package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/** PaymentCategoryDescriptorRequest class. */
@Getter
@Builder
public class PaymentCategoryDescriptorRequest {
  /** Passenger information. */
  private final PaymentPassengerRequest passenger;

  /** Flight information. */
  private final PaymentRouteRequest route;
}
