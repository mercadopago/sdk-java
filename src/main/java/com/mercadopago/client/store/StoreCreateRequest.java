package com.mercadopago.client.store;

import com.google.gson.JsonObject;
import lombok.Builder;
import lombok.Getter;

/**
 * Request DTO used to create a new store for a MercadoPago user via the Stores API.
 *
 * <p>Fields mirror the store schema of the Stores API. All fields are optional from the SDK's
 * point of view; requiredness is enforced by the API, not by this DTO.
 *
 * @see <a href="https://www.mercadopago.com/developers/en/reference/stores">Stores API Reference</a>
 * @see StoreClient#createStore(Long, StoreCreateRequest, com.mercadopago.core.MPRequestOptions)
 */
@Getter
@Builder
public class StoreCreateRequest {
  /** Name of the store. */
  private final String name;

  /** External identifier of the store, as defined by the caller. */
  private final String externalId;

  /** Opening hours of the store. */
  private final StoreBusinessHoursRequest businessHours;

  /** Geographic location of the store. */
  private final StoreLocationRequest location;
}
