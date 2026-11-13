package com.mercadopago.resources.store;

import com.google.gson.annotations.SerializedName;
import com.mercadopago.net.MPResource;
import java.time.OffsetDateTime;
import java.util.Map;
import lombok.Getter;

/**
 * Represents a store resource from the MercadoPago Stores API.
 *
 * <p>A store identifies one of the merchant's physical business locations, including its
 * opening hours ({@code business_hours}) and geographic {@code location}. Stores are managed
 * under a MercadoPago user account and can be referenced by point-of-sale and payment
 * operations.
 *
 * <p>Instances of this class are returned by the store operations of the SDK and are populated
 * by deserializing the JSON response payload of the API.
 *
 * @see <a href="https://www.mercadopago.com/developers/en/reference/stores/_users_user_id_stores/post">
 *     Stores API reference</a>
 */
@Getter
public class Store extends MPResource {

  /** Unique identifier of the store. */
  private String id;

  /** Unique identifier of the MercadoPago user that owns the store. */
  private Long userId;

  /** External identifier used by the integrator to correlate the store with its own systems. */
  private String externalId;

  /** Name that identifies the store. */
  private String name;

  /** Date and time when the store was created in MercadoPago. */
  private OffsetDateTime dateCreated;

  /** Business opening hours of the store, keyed by day of the week. */
  private Map<String, Object> businessHours;

  /** Geographic location of the store (address, city, state, coordinates, etc.). */
  private Map<String, Object> location;

  /** External identifier used by the integrator to correlate the store with its own systems. */
  private String externalReference;
}
