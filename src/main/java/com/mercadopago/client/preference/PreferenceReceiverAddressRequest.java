package com.mercadopago.client.preference;

import lombok.Builder;
import lombok.Getter;

/**
 * Receiver's shipping address for a checkout preference, used to specify where the purchased items
 * should be delivered.
 *
 * @see com.mercadopago.client.preference.PreferenceShipmentsRequest
 */
@Getter
@Builder
public class PreferenceReceiverAddressRequest {
  /** Postal or ZIP code of the address. */
  private final String zipCode;

  /** Street name of the address. */
  private final String streetName;

  /** Street number of the address. */
  private final String streetNumber;

  /** Country name of the address. */
  private final String countryName;

  /** State or province name. */
  private final String stateName;

  /** Floor number or identifier. */
  private final String floor;

  /** Apartment number or identifier. */
  private final String apartment;

  /** City name of the address. */
  private final String cityName;
}
