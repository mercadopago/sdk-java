package com.mercadopago.resources.common;

import lombok.Getter;

/**
 * Represents a phone number used across the MercadoPago API.
 *
 * <p>Holds the area code and the phone number, typically associated with payers, customers, or
 * other contact information within the MercadoPago ecosystem.
 *
 * @see <a href="https://www.mercadopago.com.br/developers/en/reference">MercadoPago API Reference</a>
 */
@Getter
public class Phone {
  /** Area code of the phone number (e.g., country or region prefix). */
  private String areaCode;

  /** Phone number without the area code. */
  private String number;
}
