package com.mercadopago.resources.preference;

import com.mercadopago.resources.common.Address;
import lombok.Getter;

/** Shipping address. */
@Getter
public class PreferenceReceiverAddress extends Address {
  /** Country. */
  private String countryName;

  /** State. */
  private String stateName;

  /** Floor. */
  private String floor;

  /** Apartment. */
  private String apartment;

  /** City name. */
  private String cityName;
}
