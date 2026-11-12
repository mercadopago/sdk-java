package com.mercadopago.resources.store;

import com.mercadopago.net.MPResource;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.Getter;

/**
 * Resource representing a physical Store location.
 *
 * <p>Stores are used to manage physical points of sale for MercadoPago merchants,
 * enabling in-person payment processing and order management.
 *
 * @see StoreLocation
 * @see StoreBusinessHours
 */
@Getter
public class Store extends MPResource {

  /** Unique identifier of the store. */
  private Long id;

  /** Name of the store. */
  private String name;

  /** Date and time when the store was created. */
  private OffsetDateTime dateCreation;

  /** Business hours for the store. */
  private List<StoreBusinessHours> businessHours;

  /** Physical location details of the store. */
  private StoreLocation location;

  /** External reference identifier for the store. */
  private String externalId;
}
