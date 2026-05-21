package com.mercadopago.resources.payment;

import lombok.Getter;

/**
 * Resource that represents the purchase order associated with a MercadoPago payment.
 *
 * <p>Links the payment to an order in the MercadoPago or MercadoLibre ecosystem,
 * allowing tracking and reconciliation between payments and orders.
 *
 * @see Payment#getOrder()
 */
@Getter
public class PaymentOrder {
  /** Unique identifier of the associated purchase order. */
  private Long id;

  /** Type of the order (e.g. mercadolibre, mercadopago). */
  private String type;
}
