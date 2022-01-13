package com.mercadopago.client.payment;

import java.util.Date;
import lombok.Builder;
import lombok.Data;

/** PaymentRouteRequest class. */
@Data
@Builder
public class PaymentRouteRequest {
  private String departure;

  private String destination;

  private Date departureDateTime;

  private Date arrivalDateTime;

  private String company;
}
