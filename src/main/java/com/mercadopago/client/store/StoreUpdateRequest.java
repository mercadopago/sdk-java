package com.mercadopago.client.store;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;

/**
 * Request DTO used to update an existing store in Mercado Pago via the
 * {@code PUT /users/{user_id}/stores/{id}} endpoint.
 *
 * <p>The Stores API request body is modeled as an opaque payload per the service specification:
 * the object is accepted as-is and serialized without inventing additional fields or
 * requiredness. Instances are created with the Lombok builder convention used across the SDK.
 *
 * @see StoreCreateRequest
 * @see <a href="https://www.mercadopago.com.br/developers/en/reference/stores">Stores API Reference</a>
 */
@Getter
@Builder
public class StoreUpdateRequest {

  /** Store name. */
  private final String name;

  /** External identifier of the store, useful to map it to an internal record. */
  @SerializedName("external_id")
  private final String externalId;

  /** Geographic location of the store. */
  private final StoreLocationRequest location;

  /** Operating schedule of the store. */
  @SerializedName("business_hours")
  private final StoreBusinessHoursRequest businessHours;
}
