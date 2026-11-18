package com.mercadopago.resources.store;

import com.mercadopago.net.MPResource;
import lombok.Getter;

/**
 * Resource representing a MercadoPago store.
 *
 * <p>Stores are physical or logical locations associated with a seller user and are used by
 * in-person payment solutions (e.g. Point of Sale devices). This class models only the fields
 * returned by the Stores API operations (create, get, search, update and delete).
 *
 * @see com.mercadopago.client.store.StoreClient
 */
@Getter
public class Store extends MPResource {

  /** Unique numeric identifier of the store. */
  private Long id;

  /** Numeric identifier of the user (seller) that owns the store. */
  private Long userId;

  /** Name of the store. */
  private String name;

  /** External identifier of the store, as provided by the integrator. */
  private String externalId;

  /** Location details of the store (address, city, state, etc.). */
  private Location location;

  /** Business hours of the store, grouped by day of the week. */
  private BusinessHours businessHours;

  /** ISO-8601 creation date of the store. */
  private String dateCreated;

  /** ISO-8601 date of the last update of the store. */
  private String dateUpdated;

  /** Location details of a store. */
  @Getter
  public static class Location {

    /** Address line of the store. */
    private String addressLine;

    /** Street name of the store address. */
    private String streetName;

    /** Street number of the store address. */
    private String streetNumber;

    /** Zip code of the store address. */
    private String zipCode;

    /** City of the store. */
    private City city;

    /** State of the store. */
    private State state;

    /** Reference information for the store location. */
    private String reference;

    /** Latitude of the store location. */
    private Double latitude;

    /** Longitude of the store location. */
    private Double longitude;
  }

  /** City of a store location. */
  @Getter
  public static class City {

    /** Identifier of the city. */
    private String id;

    /** Name of the city. */
    private String name;
  }

  /** State of a store location. */
  @Getter
  public static class State {

    /** Identifier of the state. */
    private String id;

    /** Name of the state. */
    private String name;
  }

  /** Business hours of a store, grouped by day of the week. */
  @Getter
  public static class BusinessHours {

    /** Business hours for Monday. */
    private DayHours monday;

    /** Business hours for Tuesday. */
    private DayHours tuesday;

    /** Business hours for Wednesday. */
    private DayHours wednesday;

    /** Business hours for Thursday. */
    private DayHours thursday;

    /** Business hours for Friday. */
    private DayHours friday;

    /** Business hours for Saturday. */
    private DayHours saturday;

    /** Business hours for Sunday. */
    private DayHours sunday;
  }

  /** Opening and closing hours for a single day. */
  @Getter
  public static class DayHours {

    /** Opening time (e.g. "08:00"). */
    private String open;

    /** Closing time (e.g. "18:00"). */
    private String close;
  }
}
