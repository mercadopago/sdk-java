package com.mercadopago.resources.point;

import com.mercadopago.net.MPResource;
import java.math.BigDecimal;
import lombok.Getter;

/**
 * Resource representing a payment intent created on a MercadoPago Point device.
 *
 * <p>A payment intent encapsulates a pending charge that is queued on a specific Point device.
 * It holds the amount, description, payment configuration, and optional additional information
 * such as external references and ticket printing preferences.
 *
 * @see PointPaymentIntentAdditionalInfo
 * @see PointPaymentIntentPayment
 * @see com.mercadopago.client.point.PointClient#createPaymentIntent(String,
 *     com.mercadopago.client.point.PointPaymentIntentRequest)
 */
@Getter
public class PointPaymentIntent extends MPResource {
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
}
