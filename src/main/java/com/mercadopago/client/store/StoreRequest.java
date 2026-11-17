package com.mercadopago.client.store;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;

/**
 * Request object for creating or updating a Store through the Stores API.
 *
 * <p>Fields mirror the create/update request body of the Stores endpoints
 * ({@code POST /users/{user_id}/stores} and {@code PUT /users/{user_id}/stores/{id}}).
 * Fields not yet modeled in this SDK remain opaque and are not invented here.
 *
 * @see com.mercadopago.client.store.StoreClient
 */
@Getter
@Builder
public class StoreRequest {

  /** Name of the store. */
  @SerializedName("name")
  private String name;

  /** Location of the store. */
  @SerializedName("location")
  private StoreLocationRequest location;

  /** Business hours of the store. */
  @SerializedName("business_hours")
  private StoreBusinessHoursRequest businessHours;

  /** Location details of a store, as accepted by the create/update request body. */
  @Getter
  @Builder
  public static class StoreLocationRequest {

    /** Street name of the store location. */
    @SerializedName("street_name")
    private String streetName;

    /** Street number of the store location. */
    @SerializedName("street_number")
    private String streetNumber;

    /** Zip code of the store location. */
    @SerializedName("zip_code")
    private String zipCode;

    /** Floor of the store location. */
    @SerializedName("floor")
    private String floor;

    /** Apartment of the store location. */
    @SerializedName("apartment")
    private String apartment;

    /** City name of the store location. */
    @SerializedName("city_name")
    private String cityName;

    /** State name of the store location. */
    @SerializedName("state_name")
    private String stateName;

    /** Latitude of the store location. */
    @SerializedName("latitude")
    private String latitude;

    /** Longitude of the store location. */
    @SerializedName("longitude")
    private String longitude;
  }

  /** Business hours of a store, as accepted by the create/update request body. */
  @Getter
  @Builder
  public static class StoreBusinessHoursRequest {

    /** Opening hours for Monday. */
    @SerializedName("monday")
    private String monday;

    /** Opening hours for Tuesday. */
    @SerializedName("tuesday")
    private String tuesday;

    /** Opening hours for Wednesday. */
    @SerializedName("wednesday")
    private String wednesday;

    /** Opening hours for Thursday. */
    @SerializedName("thursday")
    private String thursday;

    /** Opening hours for Friday. */
    @SerializedName("friday")
    private String friday;

    /** Opening hours for Saturday. */
    @SerializedName("saturday")
    private String saturday;

    /** Opening hours for Sunday. */
    @SerializedName("sunday")
    private String sunday;
  }
}
