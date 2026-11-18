package com.mercadopago.client.store;

import com.google.gson.JsonObject;
import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;

/**
 * Request object for updating an existing store through the Stores API
 * ({@code PUT /users/{user_id}/stores/{id}}).
 *
 * <p>Only fields evidenced by the Stores specification are modeled. All fields are optional in
 * an update request; fields left unset are not sent to the API. The {@code location} and
 * {@code business_hours} schemas are not resolved by the specification, so they are kept opaque
 * as raw JSON objects.
 *
 * @see com.mercadopago.client.store.StoreClient
 */
@Getter
@Builder
public class StoreUpdateRequest implements Serializable {

  /** Name of the store. */
  private final String name;

  /** Business hours of the store, kept opaque as a raw JSON object. */
  private final JsonObject businessHours;

  /** Geographic location of the store, kept opaque as a raw JSON object. */
  private final JsonObject location;
}
