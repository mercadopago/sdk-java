package com.mercadopago.resources.point;

import com.mercadopago.net.MPResource;
import java.math.BigDecimal;
import lombok.Getter;

/** Point Payment Intent resource. */
@Getter
public class PointSearchPaymentIntent extends MPResource {
  /** Payment intent identifier. */
  private String id;

  /** Payment intent description. */
  private String description;

  /** Identifier of the device that have a payment intent queued. */
  private String deviceId;

  /** Payment intent amount. */
  private BigDecimal amount;

  /** Payment intent additional info. */
  private PointPaymentIntentAdditionalInfo additionalInfo;

  /** Properties of the payment intent. */
  private PointPaymentIntentPayment payment;

  /** Actual state of payment intent. */
  private String state;
}
