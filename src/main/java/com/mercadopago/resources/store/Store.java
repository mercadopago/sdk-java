package com.mercadopago.resources.store;

import com.google.gson.annotations.SerializedName;
import com.mercadopago.net.MPResource;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Resource representing a physical store registered by a Mercado Pago seller for Point of Sale
 * (Point) operations.
 *
 * <p>Stores are created, retrieved, updated, and deleted through the Stores API
 * ({@code /users/{user_id}/stores} and {@code /stores/{id}} endpoints) and are used to group
 * Point of Sale devices under a business location.
 *
 * <p>Instances are returned by {@link com.mercadopago.client.store.StoreClient} and are
 * populated via Gson deserialization of the API response.
 *
 * @see com.mercadopago.client.store.StoreClient
 * @see com.mercadopago.client.store.StoreCreateRequest
 * @see com.mercadopago.client.store.StoreUpdateRequest
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Builder
public class Store extends MPResource {

  /** Unique identifier of the store, assigned by Mercado Pago. */
  private Long id;

  /** Identifier of the Mercado Pago user (seller) that owns the store. */
  private Long userId;

  /** External identifier assigned by the seller to the store, used for custom lookups. */
  @SerializedName("external_id")
  private String externalId;

  /** Name of the store. */
  private String name;

  /** Business opening hours, as configured for the store, keyed by weekday. */
  private BusinessHours businessHours;

  /** Date and time when the store was created. */
  private String dateCreated;

  /** Physical location of the store (address line, city, state, zip code, and coordinates). */
  private Location location;

  /**
   * Business opening hours of a store, with one open/close pair per weekday.
   *
   * <p>Each weekday field maps to the corresponding key of the {@code business_hours} object
   * returned by the Stores API.
   */
  @Data
  @NoArgsConstructor
  @Builder
  public static class BusinessHours {

    /** Opening and closing hours for Monday. */
    private OpenClose monday;

    /** Opening and closing hours for Tuesday. */
    private OpenClose tuesday;

    /** Opening and closing hours for Wednesday. */
    private OpenClose wednesday;

    /** Opening and closing hours for Thursday. */
    private OpenClose thursday;

    /** Opening and closing hours for Friday. */
    private OpenClose friday;

    /** Opening and closing hours for Saturday. */
    private OpenClose saturday;

    /** Opening and closing hours for Sunday. */
    private OpenClose sunday;
  }

  /** Opening and closing hours for a single weekday. */
  @Data
  @NoArgsConstructor
  @Builder
  public static class OpenClose {

    /** Opening time of the store on the weekday. */
    private String open;

    /** Closing time of the store on the weekday. */
    private String close;
  }

  /**
   * Physical address location of a store.
   *
   * <p>Describes where the store is located, including the address line, city, state,
   * postal code, and geographic coordinates (latitude and longitude).
   */
  @Data
  @NoArgsConstructor
  @Builder
  public static class Location {

    /** Address line (street and street number) of the store address. */
    private String addressLine;

    /** City where the store is located. */
    private String city;

    /** State where the store is located. */
    private String state;

    /** Postal (zip) code of the store location. */
    private String zipCode;

    /** Latitude coordinate of the store location. */
    private String latitude;

    /** Longitude coordinate of the store location. */
    private String longitude;

    /** Reference or additional information to locate the store. */
    private String reference;
  }
}
