package com.mercadopago.resources.point;

import com.mercadopago.net.MPResource;
import java.util.List;
import lombok.Getter;

/**
 * Resource representing a list of payment intent events from Point devices.
 *
 * <p>Contains a collection of {@link PointPaymentIntentListEvent} entries, each representing a
 * payment intent event with its status and creation timestamp. Used to retrieve the history of
 * payment intents processed on a device.
 *
 * @see PointPaymentIntentListEvent
 * @see com.mercadopago.client.point.PointClient#getPaymentIntentList(String)
 */
@Getter
public class PointPaymentIntentList extends MPResource {
  /** Collection of payment intent events associated with the device. */
  private List<PointPaymentIntentListEvent> events;
}
