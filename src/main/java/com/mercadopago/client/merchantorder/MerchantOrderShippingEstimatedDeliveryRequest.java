package com.mercadopago.client.merchantorder;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;

/** Estimated delivery time information. */
@Getter
@Builder
public class MerchantOrderShippingEstimatedDeliveryRequest {
  /** Estimated delivery date. */
  private final Date date;

  /** Estimated lower delivery time. */
  private final String timeFrom;

  /** Estimated upper delivery time. */
  private final String timeTo;
}
