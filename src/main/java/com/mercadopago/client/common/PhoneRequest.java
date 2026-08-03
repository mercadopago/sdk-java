package com.mercadopago.client.common;

import lombok.Builder;
import lombok.Getter;

/**
 * Request DTO representing a phone number, split into area code and number. Used across multiple
 * Mercado Pago API resources such as customers, payers, and shipping contacts.
 *
 * @see <a href="https://www.mercadopago.com.br/developers/en/reference">Mercado Pago API Reference</a>
 */
@Getter
@Builder
public class PhoneRequest {
  /** Telephone area code (e.g., "11" for Sao Paulo, "54" for Argentina). */
  private final String areaCode;

  /** Phone number without the area code. */
  private final String number;
}
