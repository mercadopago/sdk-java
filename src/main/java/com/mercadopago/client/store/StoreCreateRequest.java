package com.mercadopago.client.store;

import lombok.Builder;
import lombok.Getter;

/**
 * Request body for creating a Store via the Stores API
 * ({@code POST /users/{user_id}/stores}).
 *
 * <p>Fields follow the spec3 Stores schemas. Fields whose schemas are unresolved
 * references or empty are kept opaque as {@code Object}.
 */
@Getter
@Builder
public class StoreCreateRequest {

  /** Name of the store. */
  private final String name;

  /** Location of the store (address, coordinates, etc.). Kept opaque per spec. */
  private final Object location;

  /** Business hours of the store. Kept opaque per spec. */
  @SerializedName("business_hours")
  private final Object businessHours;
}
