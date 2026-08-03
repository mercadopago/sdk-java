package com.mercadopago.resources.preference;

import com.mercadopago.client.common.IdentificationRequest;
import lombok.Getter;

/**
 * Resource representing passenger information for travel-related preference items.
 *
 * <p>Contains the personal details and identification of a passenger, typically used in
 * airline or transportation category descriptors within a checkout preference.
 *
 * @see PreferenceCategoryDescriptor
 */
@Getter
public class PreferencePassenger {
  /** First name of the passenger. */
  private String firstName;

  /** Last name of the passenger. */
  private String lastName;

  /** Official identification document of the passenger (type and number). */
  private IdentificationRequest identification;
}
