package com.mercadopago.client.payment;

import com.mercadopago.client.common.IdentificationRequest;
import lombok.Builder;
import lombok.Getter;

/** PaymentPassengerRequest class. */
@Getter
@Builder
public class PaymentPassengerRequest {
  /** First name. */
  private final String firstName;

  /** Last name. */
  private final String lastName;

  /** Identification. */
  private final IdentificationRequest identification;
}
