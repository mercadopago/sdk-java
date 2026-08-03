package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/**
 * Request object identifying the purchase order associated with a payment.
 * Links the payment to an existing order in the MercadoPago platform.
 */
@Getter
@Builder
public class PaymentOrderRequest {
  /** Unique identifier of the associated purchase order. */
  private final Long id;

  /** Type of the order (e.g. "mercadolibre"). */
  private final String type;
}
