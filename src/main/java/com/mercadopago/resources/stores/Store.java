package com.mercadopago.resources.stores;

import com.mercadopago.net.MPResource;
import lombok.Getter;

/**
 * Resource representing a MercadoPago physical or logical store.
 *
 * <p>Stores are used to organize points of sale and track in-person payment operations.
 * Each store belongs to a user and can have associated metadata such as business hours,
 * location, and external identifiers.
 *
 * @see com.mercadopago.client.stores.StoreClient
 */
@Getter
public class Store extends MPResource {

  /** Unique identifier of the store. */
  private Long id;

  /** Name of the store. */
  private String name;

  /** Timestamp when the store was created (ISO 8601). */
  private String dateCreation;

  /** Business hours information (opaque structure). */
  private Object businessHours;

  /** Location information (opaque structure). */
  private Object location;

  /** External reference identifier provided by the integrator. */
  private String externalId;
}
