package com.mercadopago.client.point;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

/**
 * Request object to create a payment intent on a MercadoPago Point device.
 *
 * @see com.mercadopago.client.point.PointPaymentIntentPaymentRequest
 * @see com.mercadopago.client.point.PointPaymentIntentAdditionalInfoRequest
 * @see com.mercadopago.client.point.PointClient
 */
@Getter
@Builder
public class PointPaymentIntentRequest {
  /** Positive amount to charge for this payment intent. */
  private final BigDecimal amount;

  /** Description displayed for this payment intent. */
  private final String description;

  /** Payment configuration including type and installments. */
  private final PointPaymentIntentPaymentRequest payment;

  /** Additional information such as external references and printing options. */
  private final PointPaymentIntentAdditionalInfoRequest additionalInfo;
}
