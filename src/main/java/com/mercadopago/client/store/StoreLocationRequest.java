package com.mercadopago.client.store;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

/**
 * Request DTO representing a store's physical location. Used when creating or updating
 * a store to specify its address and geographic coordinates.
 *
 * @see <a href="https://www.mercadopago.com.br/developers/en/reference/stores">Stores API Reference</a>
 */
@Getter
@Builder
public class StoreLocationRequest {
  /** Street address line of the store location. */
  private final String addressLine;

  /** City where the store is located. */
  private final String city;

  /** State or province where the store is located. */
  private final String state;

  /** Country where the store is located. */
  private final String country;

  /** Geographic latitude coordinate of the store. */
  private final BigDecimal latitude;

  /** Geographic longitude coordinate of the store. */
  private final BigDecimal longitude;

  /** Postal or ZIP code of the store location. */
  private final String zipCode;
}
