package com.mercadopago.resources.preference;

import com.mercadopago.client.common.IdentificationRequest;
import lombok.Getter;

/** Passenger info. */
@Getter
public class PreferencePassenger {
  /** First name. */
  private String firstName;

  /** Last name. */
  private String lastName;

  /** Identification. */
  private IdentificationRequest identification;
}
