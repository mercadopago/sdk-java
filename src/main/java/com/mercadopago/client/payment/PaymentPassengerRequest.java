package com.mercadopago.client.payment;

import com.mercadopago.client.common.IdentificationRequest;
import lombok.Builder;
import lombok.Getter;

/** PaymentPassengerRequest class. */
@Getter
@Builder
public class PaymentPassengerRequest {
  private final String firstName;

  private final String lastName;

  private final IdentificationRequest identification;
}
