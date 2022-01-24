package com.mercadopago.resources.merchantorder;

import java.util.Date;
import lombok.Getter;

/** Estimated delivery time information. */
@Getter
public class MerchantOrderShippingEstimatedDelivery {
  /** Estimated delivery date. */
  private Date date;

  /** Estimated lower delivery time. */
  private String timeFrom;

  /** Estimated upper delivery time. */
  private String timeTo;
}
