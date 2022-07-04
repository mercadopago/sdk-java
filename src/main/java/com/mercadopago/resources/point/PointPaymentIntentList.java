package com.mercadopago.resources.point;

import com.mercadopago.net.MPResource;
import java.util.List;
import lombok.Getter;

/** PointPaymentIntentList class. */
@Getter
public class PointPaymentIntentList extends MPResource {
  /** List of events. */
  private List<PointPaymentIntentListEvent> events;
}
