package com.mercadopago.resources.point;

import com.mercadopago.net.MPResource;
import lombok.Getter;

/** Point Payment Intent delete resource. */
@Getter
public class PointPaymentIntentDelete extends MPResource {
  /** Payment intent ID. */
  private String id;
}
