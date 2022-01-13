package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Data;

/** PaymentCategoryDescriptorRequest class. */
@Data
@Builder
public class PaymentCategoryDescriptorRequest {
  private PaymentPassengerRequest passenger;

  private PaymentRouteRequest route;
}
