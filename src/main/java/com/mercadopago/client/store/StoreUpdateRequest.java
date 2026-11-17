package com.mercadopago.client.store;

import lombok.Builder;
import lombok.Getter;

/**
 * Request DTO used to update an existing Store. Only the fields present in the
 * PUT /users/{user_id}/stores/{id} request body are exposed; any field left unset is
 * not modified.
 *
 * @see <a href="https://www.mercadopago.com.br/developers/en/reference/stores">Stores API Reference</a>
 */
@Getter
@Builder
public class StoreUpdateRequest {

  /** Name of the store. */
  private final String name;

  /** Geographic location of the store. */
  private final Object location;

  /** Business hours of the store. */
  private final Object businessHours;
}
