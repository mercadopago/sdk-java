package com.mercadopago.resources.store;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mercadopago.net.MPResource;
import lombok.Getter;
import lombok.Setter;

/**
 * Resource representing a MercadoPago store.
 *
 * <p>Stores group physical or online points of sale (POS) belonging to a seller account. They are
 * managed through the Stores API under the {@code /users/{user_id}/stores} endpoints.
 *
 * @see com.mercadopago.client.user.UserClient
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Store extends MPResource {

  /** Unique identifier of the store. */
  private Long id;

  /** Name of the store. */
  private String name;

  /** Business hours of the store, keyed by day of the week. */
  private Object businessHours;

  /** Location of the store. */
  private Object location;

  /** Identifier of the user account that owns the store. */
  private Long userId;

  /** Date of creation of the store. */
  private String dateCreated;

  /** Date of the last update of the store. */
  private String dateLastUpdated;
}
