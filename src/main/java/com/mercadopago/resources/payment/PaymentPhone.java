package com.mercadopago.resources.payment;

import lombok.Getter;

/**
 * Resource that represents a phone number associated with a MercadoPago payment payer.
 *
 * <p>Contains the area code and phone number, used for contact and fraud prevention purposes.
 *
 * @see PaymentAdditionalInfoPayer#getPhone()
 */
@Getter
public class PaymentPhone {
  /** Area code of the phone number (e.g. 11, 21, 55). */
  private String areaCode;

  /** Phone number without the area code. */
  private String number;
}
