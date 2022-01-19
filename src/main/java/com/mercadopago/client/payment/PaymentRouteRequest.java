package com.mercadopago.client.payment;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;

/** PaymentRouteRequest class. */
@Getter
@Builder
public class PaymentRouteRequest {
  private final String departure;

  private final String destination;

  private final Date departureDateTime;

  private final Date arrivalDateTime;

  private final String company;
}
