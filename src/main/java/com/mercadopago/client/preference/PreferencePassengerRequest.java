package com.mercadopago.client.preference;

import com.mercadopago.client.common.IdentificationRequest;
import lombok.Builder;
import lombok.Getter;

/** Passenger info. */
@Getter
@Builder
public class PreferencePassengerRequest {
  /** First name. */
  private final String firstName;

  /** Last name. */
  private final String lastName;

  /** Identification. */
  private final IdentificationRequest identification;
}
