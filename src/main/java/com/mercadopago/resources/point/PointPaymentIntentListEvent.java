package com.mercadopago.resources.point;

import java.time.OffsetDateTime;
import lombok.Getter;

/** PointPaymentIntentListEvent class. */
@Getter
public class PointPaymentIntentListEvent {
  /** Payment intent ID. */
  private String paymentIntentId;

  /** Payment status. */
  private String status;

  /** Payment creation date. */
  private OffsetDateTime createdOn;
}
