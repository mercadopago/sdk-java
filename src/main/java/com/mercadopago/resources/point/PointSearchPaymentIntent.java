package com.mercadopago.resources.point;

import com.mercadopago.net.MPResource;
import java.math.BigDecimal;
import lombok.Getter;

/**
 * Resource representing the result of searching for a specific payment intent on a Point device.
 *
 * <p>Extends the standard payment intent data with an additional {@code state} field that indicates
 * the current lifecycle state of the intent. This resource is returned when querying for a
 * previously created payment intent by its identifier.
 *
 * @see PointPaymentIntent
 * @see PointPaymentIntentAdditionalInfo
 * @see PointPaymentIntentPayment
 * @see com.mercadopago.client.point.PointClient#searchPaymentIntent(String, String)
 */
@Getter
public class PointSearchPaymentIntent extends MPResource {
  /** Unique identifier of the payment intent. */
  private String id;

  /** Short description of the payment intent shown on the device. */
  private String description;

  /** Identifier of the Point device where this payment intent is queued. */
  private String deviceId;

  /** Total amount to be charged for this payment intent. */
  private BigDecimal amount;

  /** Additional metadata such as external reference, ticket number, and print settings. */
  private PointPaymentIntentAdditionalInfo additionalInfo;

  /** Payment configuration including type, installments, and cost allocation. */
  private PointPaymentIntentPayment payment;

  /** Current lifecycle state of the payment intent (e.g., OPEN, FINISHED, CANCELED, ERROR). */
  private String state;
}
