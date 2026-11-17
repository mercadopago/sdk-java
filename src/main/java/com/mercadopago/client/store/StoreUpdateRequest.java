package com.mercadopago.client.store;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;

/**
 * Request DTO used to update an existing store ({@code PUT /users/{user_id}/stores/{id}}).
 *
 * <p>Only the attributes that the Stores API accepts on update are modeled here. Any field left
 * as {@code null} is omitted from the JSON payload by the serializer, so only the provided
 * attributes are updated.
 *
 * @see <a
 *     href="https://www.mercadopago.com/developers/en/reference/stores/_users_user_id_stores_id/put">
 *     Stores API reference</a>
 * @see StoreClient#update(Long, String, StoreUpdateRequest)
 */
@Getter
@Builder
public class StoreUpdateRequest {
  /** Name of the store to be updated. */
  private final String name;

  /** Geographic location of the store. */
  private final LocationRequest location;

  /** Business hours of the store. */
  private final BusinessHoursRequest businessHours;

  /** Additional data about the store. */
  private final StoreUpdateAdditionalInfoRequest additionalInfo;

  /**
   * Geographic location of the store included in an update request.
   *
   * <p>Mirrors the {@code location} object of the store update request schema.
   */
  @Getter
  @Builder
  public static class LocationRequest {
    /** Street number of the store address. */
    private final Integer streetNumber;

    /** Street name of the store address. */
    private final String streetName;

    /** City name where the store is located. */
    private final String cityName;

    /** State name where the store is located. */
    private final String stateName;

    /** Latitude coordinate of the store location. */
    private final Double latitude;

    /** Longitude coordinate of the store location. */
    private final Double longitude;

    /** Reference to help find the store location. */
    private final String reference;
  }

  /**
   * Business hours of the store included in an update request.
   *
   * <p>Mirrors the {@code business_hours} object of the store update request schema.
   */
  @Getter
  @Builder
  public static class BusinessHoursRequest {
    /** Business hours for Monday. */
    private final DayHoursRequest monday;

    /** Business hours for Tuesday. */
    private final DayHoursRequest tuesday;

    /** Business hours for Wednesday. */
    private final DayHoursRequest wednesday;

    /** Business hours for Thursday. */
    private final DayHoursRequest thursday;

    /** Business hours for Friday. */
    private final DayHoursRequest friday;

    /** Business hours for Saturday. */
    private final DayHoursRequest saturday;

    /** Business hours for Sunday. */
    private final DayHoursRequest sunday;
  }

  /**
   * Open and close hours for a single day of the week.
   */
  @Getter
  @Builder
  public static class DayHoursRequest {
    /** Opening hour of the store (e.g. {@code "08:00"}). */
    private final String open;

    /** Closing hour of the store (e.g. {@code "18:00"}). */
    private final String close;
  }

  /**
   * Additional data about the store included in an update request.
   *
   * <p>Mirrors the {@code additional_info} object of the store update request schema.
   */
  @Getter
  @Builder
  public static class StoreUpdateAdditionalInfoRequest {
    /** External identifier of the store in the integrator's system. */
    private final String externalId;
  }
}
