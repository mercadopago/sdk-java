package com.mercadopago.client.store;

import lombok.Builder;
import lombok.Getter;

/**
 * Request object for updating an existing store through the Stores API.
 *
 * <p>All fields are optional: the API accepts a partial payload and only updates the
 * attributes that are present. No requiredness or validation is imposed beyond what the
 * API specification exposes.
 *
 * <p>Usage example:
 * <pre>{@code
 * StoreUpdateRequest request = StoreUpdateRequest.builder()
 *     .name("Store name")
 *     .businessHours(StoreBusinessHoursRequest.builder().build())
 *     .location(StoreLocationRequest.builder().build())
 *     .externalId("external-123")
 *     .build();
 * }</pre>
 *
 * @see StoreClient#update(Long, String, StoreUpdateRequest)
 */
@Getter
@Builder
public class StoreUpdateRequest {

  /** Name of the store. */
  private final String name;

  /** Business hours of the store. */
  private final StoreBusinessHoursRequest businessHours;

  /** Location of the store. */
  private final StoreLocationRequest location;

  /** External identifier used to correlate the store with the integrator's system. */
  private final String externalId;
}
