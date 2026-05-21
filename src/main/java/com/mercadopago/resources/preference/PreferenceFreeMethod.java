package com.mercadopago.resources.preference;

import lombok.Getter;

/**
 * Resource representing a shipping method offered as free shipping in a checkout preference.
 *
 * <p>Identifies a specific shipping method by its ID that the seller offers at no cost to the
 * buyer. Only applicable when the shipment mode is {@code me2}.
 *
 * @see PreferenceShipments
 */
@Getter
public class PreferenceFreeMethod {
  /** Unique identifier of the shipping method offered for free. */
  private Long id;
}
