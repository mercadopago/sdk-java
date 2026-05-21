package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/**
 * Request object representing the payer's phone information in a payment request.
 * Contains the area code and phone number for contacting the payer.
 */
@Getter
@Builder
public class PaymentPayerPhoneRequest {
  /** Telephone area code (e.g. "11", "21"). */
  private String areaCode;

  /** Telephone number without the area code. */
  private String number;
}
