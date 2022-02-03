package com.mercadopago.resources.preference;

import lombok.Getter;

/** Item information related to the category. */
@Getter
public class PreferenceCategoryDescriptor {
  /** Passenger information. */
  private PreferencePassenger passenger;

  /** Flight information. */
  private PreferenceRoute route;
}
