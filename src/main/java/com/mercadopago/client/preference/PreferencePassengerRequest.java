package com.mercadopago.client.preference;

import com.mercadopago.client.common.IdentificationRequest;
import lombok.Builder;
import lombok.Getter;

/**
 * Passenger information associated with a travel-related preference item, including name and
 * identification details.
 *
 * @see com.mercadopago.client.preference.PreferenceCategoryDescriptorRequest
 */
@Getter
@Builder
public class PreferencePassengerRequest {
  /** Passenger's first name. */
  private final String firstName;

  /** Passenger's last name. */
  private final String lastName;

  /** Passenger's identification document details. */
  private final IdentificationRequest identification;
}
