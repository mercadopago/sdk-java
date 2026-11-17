package com.mercadopago.resources.store;

import com.mercadopago.net.MPResource;
import lombok.Builder;
import lombok.Getter;

/**
 * Resource representing a MercadoPago store.
 *
 * <p>Stores group physical points of sale so that in-person payments can be associated with a
 * specific business location. Fields mirror the store schema exposed by the API; nested objects
 * (business hours and location) are kept opaque as raw JSON to avoid inventing structure that is
 * not documented.
 *
 * @see com.mercadopago.client.store.StoreClient
 */
@Getter
@Builder
public class Store extends MPResource {

  /** Unique identifier of the store. */
  private String id;

  /** Numeric identifier of the user (merchant) that owns the store. */
  private Long userId;

  /** Name of the store. */
  private String name;

  /** Opening hours of the store, kept opaque as raw JSON. */
  private Object businessHours;

  /** Geographic location of the store, kept opaque as raw JSON. */
  private Object location;

  /** ISO-8601 date and time when the store was created. */
  private String dateCreated;
}
