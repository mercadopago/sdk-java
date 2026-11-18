package com.mercadopago.resources.store;

import com.mercadopago.net.MPResource;
import lombok.Getter;
import lombok.Setter;

/** Store resource of a MercadoPago seller, as returned by the Stores API. */
@Getter
@Setter
public class Store extends MPResource {

  /** Unique identifier of the store. */
  private Long id;

  /** Name of the store. */
  private String name;

  /** Identifier of the user (seller) that owns the store. */
  private Long userId;

  /** External identifier assigned by the integrator to the store. */
  private String externalId;

  /** Physical location of the store. Kept opaque per spec. */
  private Map<String, Object> location;

  /** Opening and closing hours of the store, grouped by day of the week. Kept opaque per spec. */
  private Map<String, Object> businessHours;

  /** Date when the store was created, in ISO-8601 format. */
  private String dateCreated;

  /** Opening hours for Monday. */
  private Day openDay1;

  /** Opening hours for Tuesday. */
  private Day openDay2;

  /** Opening hours for Wednesday. */
  private Day openDay3;

  /** Opening hours for Thursday. */
  private Day openDay4;

  /** Opening hours for Friday. */
  private Day openDay5;

  /** Opening hours for Saturday. */
  private Day openDay6;

  /** Opening hours for Sunday. */
  private Day openDay0;

  /** Opening and closing times for a single day of the week. */
  @Getter
  @Setter
  public static class Day {

    /** Local opening hour of the store, in ISO-8601 time format. */
    private String hours;

    /** Day identifier of the store schedule. */
    private String day;
  }
}
