package com.mercadopago.resources.point;

import com.mercadopago.net.MPResource;
import java.time.OffsetDateTime;
import lombok.Getter;

/**
 * Resource representing the current status of a payment intent on a Point device.
 *
 * <p>Provides a lightweight view of a payment intent's processing status along with its creation
 * timestamp. Useful for polling or checking the progress of a payment intent.
 *
 * @see PointPaymentIntent
 * @see com.mercadopago.client.point.PointClient#getPaymentIntentStatus(String, String)
 */
@Getter
public class PointStatusPaymentIntent extends MPResource {
  /** Current processing status of the payment intent. */
  private String status;

  /** Timestamp when the payment intent was created. */
  private OffsetDateTime createdOn;
}
