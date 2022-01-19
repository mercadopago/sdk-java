package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/** PaymentCategoryDescriptorRequest class. */
@Getter
@Builder
public class PaymentCategoryDescriptorRequest {
  private final PaymentPassengerRequest passenger;

  private final PaymentRouteRequest route;
}
