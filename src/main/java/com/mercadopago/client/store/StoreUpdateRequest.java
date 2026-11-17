package com.mercadopago.client.store;

import lombok.Builder;
import lombok.Getter;


/**
 * Request body for updating an existing store via the Stores API.
 *
 * <p>Fields mirror the store schema of the MercadoPago Stores API. All fields are optional;
 * only the fields set by the caller are sent in the request body.
 *
 * @see StoreClient#updateStore(Long, String, StoreUpdateRequest, com.mercadopago.core.MPRequestOptions)
 */
@Getter
@Builder
public class StoreUpdateRequest {

  /** Name of the store. */
  private String name;
}
