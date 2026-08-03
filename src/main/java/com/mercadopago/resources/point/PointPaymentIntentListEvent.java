package com.mercadopago.resources.point;

import java.time.OffsetDateTime;
import lombok.Getter;

/**
 * Resource representing an individual event in the payment intent history of a Point device.
 *
 * <p>Each event captures a snapshot of a payment intent at a given point in time, including its
 * current processing status and the timestamp when it was created.
 *
 * @see PointPaymentIntentList
 */
@Getter
public class PointPaymentIntentListEvent {
  /** Unique identifier of the payment intent associated with this event. */
  private String paymentIntentId;

  /** Current processing status of the payment intent (e.g., open, finished, cancelled). */
  private String status;

  /** Timestamp when the payment intent was created. */
  private OffsetDateTime createdOn;
}
