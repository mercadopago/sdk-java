package com.mercadopago.client.store;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

/**
 * Request DTO used to create a store in Mercado Pago. Stores represent physical or virtual
 * locations where a merchant operates, and can be associated with QR code payment integrations.
 *
 * @see <a href="https://www.mercadopago.com/developers/en/reference/stores">Stores API Reference</a>
 */
@Getter
@Builder
public class StoreCreateRequest {
  /** Name of the store. */
  private final String name;

  /** Physical location details of the store. */
  private final StoreLocationRequest location;

  /** List of business hours for the store. */
  private final List<StoreBusinessHoursRequest> businessHours;

  /** External identifier provided by the integrator. */
  private final String externalId;
}
