package com.mercadopago.resources.preference;

import java.time.OffsetDateTime;
import lombok.Getter;

/** Item information related to the category. */
@Getter
public class PreferenceCategoryDescriptor {
  /** Passenger information. */
  private PreferencePassenger passenger;

  /** Flight information. */
  private PreferenceRoute route;

  /** Date of event */
  private OffsetDateTime eventDate;
}
