package com.mercadopago.resources.point;

import com.mercadopago.net.MPResource;
import lombok.Getter;

/** Point cancel payment intent resource. */
@Getter
public class PointCancelPaymentIntent extends MPResource {
  /** Payment intent ID. */
  private String id;
}
