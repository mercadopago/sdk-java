package com.mercadopago.resources.payment;

import lombok.Getter;

/**
 * Resource that identifies the application that originated a MercadoPago payment.
 *
 * <p>Contains the name and version of the client application used at the point of
 * interaction to create the payment.
 *
 * @see PaymentPointOfInteraction#getApplicationData()
 */
@Getter
public class PaymentApplicationData {
  /** Name of the application that created the payment. */
  private String name;

  /** Version of the application that created the payment. */
  private String version;
}
