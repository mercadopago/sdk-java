package com.mercadopago.resources.point;

import com.mercadopago.net.MPResource;
import java.time.OffsetDateTime;
import lombok.Getter;

/** Payment intent status resource. */
@Getter
public class PointStatusPaymentIntent extends MPResource {
  /** Payment intent status. */
  private String status;

  /** Payment intent creation date. */
  private OffsetDateTime createdOn;
}
