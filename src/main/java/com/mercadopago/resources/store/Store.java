package com.mercadopago.resources.store;

import com.google.gson.annotations.SerializedName;
import com.mercadopago.net.MPResource;
import lombok.Getter;

/**
 * Resource representing a MercadoPago store.
 *
 * <p>Stores are physical or logical branches registered under a seller user account, used for
 * in-person payments (e.g. Point of Sale devices). A store holds business information such as
 * its name, opening hours, location, and an external identifier supplied by the integrator.
 *
 * <p>Instances are returned by the create, get, update and search operations of {@link
 * com.mercadopago.client.store.StoreClient}.
 *
 * @see com.mercadopago.client.store.StoreClient
 */
@Getter
public class Store extends MPResource {

  /** Unique numeric identifier of the store. */
  private Long id;

  /** Numeric identifier of the user (seller) that owns the store. */
  @SerializedName("user_id")
  private Long userId;

  /** Name of the store. */
  private String name;

  /** Opening hours of the store, as supplied by the integrator. */
  @SerializedName("business_hours")
  private Object businessHours;

  /** ISO code of the timezone the store operates in (e.g. America/Argentina/Buenos_Aires). */
  @SerializedName("time_zone")
  private String timeZone;

  /** Properties describing the geographic location of the store. */
  private StoreLocation location;

  /** External identifier supplied by the integrator when the store was created. */
  private String externalId;

  /** Date when the store was created, in ISO 8601 format. */
  @SerializedName("date_created")
  private String dateCreated;

  /**
   * Nested location properties of the store as declared by the Store response schema.
   */
  @Getter
  public static class StoreLocation {

    /** Street address line of the store (street name and number). */
    @SerializedName("address_line")
    private String addressLine;

    /** Street name of the store address. */
    @SerializedName("street_name")
    private String streetName;

    /** House or street number of the store address. */
    @SerializedName("street_number")
    private String streetNumber;

    /** Name of the city where the store is located. */
    @SerializedName("city")
    private String city;

    /** Name of the state or province where the store is located. */
    @SerializedName("state")
    private String state;

    /** Postal code of the store address. */
    @SerializedName("zip_code")
    private String zipCode;

    /** Latitude coordinate of the store location. */
    private Double latitude;

    /** Longitude coordinate of the store location. */
    private Double longitude;

    /** Reference point or additional address hint (e.g. a nearby landmark). */
    private String reference;
  }
}
