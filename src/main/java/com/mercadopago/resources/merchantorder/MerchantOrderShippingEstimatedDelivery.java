package com.mercadopago.resources.merchantorder;

import java.time.OffsetDateTime;
import lombok.Getter;

/**
 * Represents estimated delivery information for a shipping option in the MercadoPago API.
 *
 * <p>Provides the estimated delivery date and a time window (from/to) within which the shipment
 * is expected to arrive.
 *
 * @see MerchantOrderShippingOption
 */
@Getter
public class MerchantOrderShippingEstimatedDelivery {
  /** Estimated date of delivery. */
  private OffsetDateTime date;

  /** Start of the estimated delivery time window. */
  private String timeFrom;

  /** End of the estimated delivery time window. */
  private String timeTo;
}
