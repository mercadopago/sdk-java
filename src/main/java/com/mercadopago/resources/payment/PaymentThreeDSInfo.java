package com.mercadopago.resources.payment;

import lombok.Getter;

/**
 * Resource that holds 3D Secure (3DS) authentication information for a MercadoPago payment.
 *
 * <p>Contains the external resource URL for the 3DS challenge flow and the challenge request
 * (creq) data needed to complete cardholder authentication.
 *
 * @see Payment#getThreeDSInfo()
 */
@Getter
public class PaymentThreeDSInfo {
  /** URL of the external 3DS challenge page where the cardholder completes authentication. */
  private String externalResourceUrl;

  /** Challenge request (creq) payload used in the 3DS authentication flow. */
  private String creq;
}
