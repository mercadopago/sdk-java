package com.mercadopago.resources.point;

import com.mercadopago.net.MPResource;
import lombok.Getter;

/**
 * Resource representing the result of cancelling a payment intent on a Point device.
 *
 * <p>Returned after successfully requesting the cancellation of a previously created payment
 * intent. Contains the identifier of the cancelled intent for confirmation purposes.
 *
 * @see com.mercadopago.client.point.PointClient#cancelPaymentIntent(String, String)
 */
@Getter
public class PointCancelPaymentIntent extends MPResource {
  /** Unique identifier of the cancelled payment intent. */
  private String id;
}
