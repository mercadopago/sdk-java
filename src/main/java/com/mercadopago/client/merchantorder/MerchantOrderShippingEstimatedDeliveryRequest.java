package com.mercadopago.client.merchantorder;

import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Getter;

/**
 * Request DTO representing the estimated delivery window for a Merchant Order shipping option.
 * Provides the expected delivery date along with a time range within that day.
 *
 * @see <a href="https://www.mercadopago.com.br/developers/en/reference/merchant_orders">Merchant Orders API Reference</a>
 */
@Getter
@Builder
public class MerchantOrderShippingEstimatedDeliveryRequest {
  /** Estimated date of delivery. */
  private final OffsetDateTime date;

  /** Start of the estimated delivery time window (e.g., "09:00"). */
  private final String timeFrom;

  /** End of the estimated delivery time window (e.g., "18:00"). */
  private final String timeTo;
}
