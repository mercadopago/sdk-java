package com.mercadopago.resources.merchantorder;

import java.time.OffsetDateTime;
import lombok.Getter;

/** Estimated delivery time information. */
@Getter
public class MerchantOrderShippingEstimatedDelivery {
  /** Estimated delivery date. */
  private OffsetDateTime date;

  /** Estimated lower delivery time. */
  private String timeFrom;

  /** Estimated upper delivery time. */
  private String timeTo;
}
