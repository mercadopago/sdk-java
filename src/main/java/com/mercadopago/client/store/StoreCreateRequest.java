package com.mercadopago.client.store;

import lombok.Builder;
import lombok.Getter;

/**
 * Request DTO used to create a store in Mercado Pago via
 * {@code POST /users/{user_id}/stores}.
 *
 * <p>Fields follow the Stores API schema. No requiredness or validation is enforced by the
 * SDK beyond what the API specification exposes.
 *
 * @see StoreClient#create(Long, StoreCreateRequest)
 * @see <a href="https://www.mercadopago.com/developers/en/reference/stores/_users_user_id_stores/post">
 *     Stores API reference</a>
 */
@Getter
@Builder
public class StoreCreateRequest {
  /** Name of the store. */
  private final String name;

  /** Business hours of the store. */
  private final StoreBusinessHoursRequest businessHours;

  /** Geographic location of the store. */
  private final StoreLocationRequest location;

  /** External identifier used to correlate the store with the integrator's system. */
  private final String externalId;

  /** Opening hours for a single weekday, as exposed by the Stores API. */
  @Getter
  @Builder
  public static final class StoreBusinessHoursRequest {
    /** Day of the week this entry applies to (e.g. {@code monday}). */
    private final String day;

    /** Opening time of the store on this day. */
    private final String open;

    /** Closing time of the store on this day. */
    private final String close;
  }

  /** Geographic location of a store, as exposed by the Stores API. */
  @Getter
  @Builder
  public static final class StoreLocationRequest {
    /** Street address lines of the store. */
    private final java.util.List<String> addressLine;

    /** Additional reference to locate the store. */
    private final String reference;

    /** City where the store is located. */
    private final StoreCityRequest city;

    /** State where the store is located. */
    private final String state;

    /** Postal code of the store location. */
    private final String zipCode;

    /** Latitude of the store location. */
    private final Double latitude;

    /** Longitude of the store location. */
    private final Double longitude;
  }

  /** City information of a store location, as exposed by the Stores API. */
  @Getter
  @Builder
  public static final class StoreCityRequest {
    /** Identifier of the city. */
    private final String id;

    /** Name of the city. */
    private final String name;
  }
}
