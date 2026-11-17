package com.mercadopago.client.store;

import com.google.gson.annotations.SerializedName;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

/**
 * Request DTO used to create a new Store via POST /users/{user_id}/stores.
 *
 * @see <a href="https://www.mercadopago.com.br/developers/en/reference/stores">Stores API Reference</a>
 */
@Getter
@Builder
public class StoreCreateRequest {
  /** Name of the store. */
  private final String name;

  /** External identifier used to correlate the store with the merchant's own system. */
  private final String externalId;

  /** Geographic location of the store. */
  private final StoreLocationRequest location;

  /** Business hours of the store, keyed by day of the week. */
  private final Map<String, Object> businessHours;

  /** Geographic location details of a store. */
  @Getter
  @Builder
  public static class StoreLocationRequest {
    /** Address line of the store (e.g., street and number). */
    private final String addressLine;

    /** Street name of the store address. */
    private final String streetName;

    /** Street number of the store address. */
    private final String streetNumber;

    /** Zip code of the store address. */
    private final String zipCode;

    /** City name of the store address. */
    private final String cityName;

    /** State name of the store address. */
    private final String stateName;

    /** Latitude of the store location. */
    private final Double latitude;

    /** Longitude of the store location. */
    private final Double longitude;

    /** Additional reference to find the store location. */
    private final String reference;
  }
}
