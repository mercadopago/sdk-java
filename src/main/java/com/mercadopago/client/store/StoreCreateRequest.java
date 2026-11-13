package com.mercadopago.client.store;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;

/**
 * Request DTO used to create a store in Mercado Pago via the
 * {@code POST /users/{user_id}/stores} endpoint.
 *
 * <p>Instances are created with the Lombok builder convention used across the SDK and are
 * serialized with snake_case field names matching the Stores API request body.
 *
 * @see StoreUpdateRequest
 * @see <a href="https://www.mercadopago.com.br/developers/en/reference/stores">Stores API Reference</a>
 */
@Getter
@Builder
public class StoreCreateRequest {

  /** Store name. */
  private final String name;

  /** External identifier of the store, useful to map it to an internal record. */
  @SerializedName("external_id")
  private final String externalId;

  /** Geographic location of the store. */
  private final StoreLocationRequest location;

  /** Opening and closing hours of the store, keyed by weekday. */
  @SerializedName("business_hours")
  private final StoreBusinessHoursRequest businessHours;

  /** Geographic location of a store, serialized with snake_case field names. */
  @Getter
  @Builder
  public static class StoreLocationRequest {

    /** Street name of the store address. */
    private final String streetName;

    /** Street number of the store address. */
    private final String streetNumber;

    /** Zip code of the store address. */
    @SerializedName("zip_code")
    private final String zipCode;

    /** Additional address details, such as floor or apartment. */
    private final String additionalInfo;

    /** City where the store is located. */
    private final StoreCityRequest city;

    /** State where the store is located. */
    private final StoreStateRequest state;

    /** Latitude coordinate of the store location. */
    private final Double latitude;

    /** Longitude coordinate of the store location. */
    private final Double longitude;
  }

  /** City of a store location. */
  @Getter
  @Builder
  public static class StoreCityRequest {

    /** City identifier. */
    private final String id;

    /** City name. */
    private final String name;
  }

  /** State of a store location. */
  @Getter
  @Builder
  public static class StoreStateRequest {

    /** State identifier. */
    private final String id;

    /** State name. */
    private final String name;
  }

  /** Opening and closing hours of a store, keyed by weekday. */
  @Getter
  @Builder
  public static class StoreBusinessHoursRequest {

    /** Monday opening hours, in the format {@code HH:mm-HH:mm}. */
    private final String monday;

    /** Tuesday opening hours, in the format {@code HH:mm-HH:mm}. */
    private final String tuesday;

    /** Wednesday opening hours, in the format {@code HH:mm-HH:mm}. */
    private final String wednesday;

    /** Thursday opening hours, in the format {@code HH:mm-HH:mm}. */
    private final String thursday;

    /** Friday opening hours, in the format {@code HH:mm-HH:mm}. */
    private final String friday;

    /** Saturday opening hours, in the format {@code HH:mm-HH:mm}. */
    private final String saturday;

    /** Sunday opening hours, in the format {@code HH:mm-HH:mm}. */
    private final String sunday;
  }
}
