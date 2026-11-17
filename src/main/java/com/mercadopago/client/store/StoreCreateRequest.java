package com.mercadopago.client.store;

import lombok.Builder;
import lombok.Getter;

/**
 * Request DTO used to create a new Store through the
 * <code>POST /users/{user_id}/stores</code> endpoint. A store represents a physical
 * branch of a merchant where payments can be collected.
 *
 * <p>The request body is kept opaque with respect to nested structures: only the documented
 * fields are exposed, without imposing any requiredness or validation rules.</p>
 *
 * @see <a href="https://www.mercadopago.com.br/developers/en/reference/stores/_users_user_id_stores/post">Stores API Reference</a>
 */
@Getter
@Builder
public class StoreCreateRequest {
  /** Name of the store. */
  private final String name;

  /** Location of the store, including geographic coordinates and address information. */
  private final Object location;

  /** Business hours during which the store is open for operation. */
  private final Object businessHours;
}
