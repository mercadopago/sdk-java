package com.mercadopago.resources.preference;

import com.mercadopago.resources.common.Address;
import lombok.Getter;

/**
 * Resource representing the shipping receiver's address in a checkout preference.
 *
 * <p>Extends the base {@link Address} with additional location details such as country name,
 * state name, city, floor, and apartment. Used to specify where the shipment should be delivered.
 *
 * @see PreferenceShipments
 * @see Address
 */
@Getter
public class PreferenceReceiverAddress extends Address {
  /** Full name of the country for the delivery address. */
  private String countryName;

  /** Full name of the state or province for the delivery address. */
  private String stateName;

  /** Floor number within the building. */
  private String floor;

  /** Apartment or unit identifier within the building. */
  private String apartment;

  /** Full name of the city for the delivery address. */
  private String cityName;
}
