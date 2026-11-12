package com.mercadopago.resources.store;

import com.mercadopago.net.MPResource;
import lombok.Getter;

/**
 * Resource representing the physical location of a Store.
 *
 * <p>Contains address and geographic coordinates for a store location.
 *
 * @see Store
 */
@Getter
public class StoreLocation extends MPResource {

  /** Street address line 1. */
  private String streetName;

  /** Street number. */
  private String streetNumber;

  /** City name. */
  private String city;

  /** State or province code. */
  private String state;

  /** Latitude coordinate. */
  private String latitude;

  /** Longitude coordinate. */
  private String longitude;

  /** Postal or ZIP code. */
  private String zipCode;
}
