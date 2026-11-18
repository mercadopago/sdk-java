package com.mercadopago.client.store;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;

/** Request type for updating an existing store (PUT /users/{user_id}/stores/{id}). */
@Getter
@Builder
public class StoreUpdateRequest {

  /** Name of the store. */
  private final String name;

  /** Business hours of the store, kept opaque per the spec schema. */
  @SerializedName("business_hours")
  private final Object businessHours;

  /** Location of the store, kept opaque per the spec schema. */
  private final Object location;
}
