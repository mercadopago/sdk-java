package com.mercadopago.resources.store;

import com.mercadopago.net.MPResource;
import lombok.Builder;
import lombok.Getter;

/**
 * Resource representing a Mercado Pago store registered by a seller.
 *
 * <p>Stores group physical or logical points of sale under a user account. A store is created
 * under a specific {@code user_id} and is referenced by point-of-sale devices when processing
 * in-person payments.
 *
 * <p>Only the fields documented in the API specification are exposed; optional and
 * spec-dependent structures such as {@code location} and {@code business_hours} remain opaque.
 *
 * @see com.mercadopago.client.store.StoreClient
 */
@Getter
@Builder
public class Store extends MPResource {

  /** Unique identifier of the store. */
  private Long id;

  /** Unique identifier of the Mercado Pago user that owns the store. */
  private Long userId;

  /** Name of the store. */
  private String name;

  /** Date and time when the store was created. */
  private String dateCreated;

  /** Location details of the store. Structure kept opaque as defined by the API specification. */
  private Object location;

  /** Opening hours of the store. Structure kept opaque as defined by the API specification. */
  private Object businessHours;

  /** External identifier that can be used to correlate the store with an external system. */
  private String externalId;
}
