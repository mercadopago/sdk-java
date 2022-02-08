package com.mercadopago.client.preference;

import lombok.Builder;
import lombok.Getter;

/** Shipping address. */
@Getter
@Builder
public class PreferenceReceiverAddressRequest {
  /** Zip code. */
  private final String zipCode;

  /** Street name. */
  private final String streetName;

  /** Street number. */
  private final String streetNumber;

  /** Country. */
  private final String countryName;

  /** State. */
  private final String stateName;

  /** Floor. */
  private final String floor;

  /** Apartment. */
  private final String apartment;

  /** City name. */
  private final String cityName;
}
