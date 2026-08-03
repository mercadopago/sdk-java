package com.mercadopago.client.cardtoken;

import com.mercadopago.resources.customer.CustomerCardCardholder;
import lombok.Builder;
import lombok.Getter;

/**
 * Request DTO used to create a card token directly from raw card data. Allows tokenization of a
 * card number, expiration, and security code without requiring a pre-saved card or customer ID.
 *
 * @see <a href="https://www.mercadopago.com.br/developers/en/reference/card_tokens">Card Token API Reference</a>
 */
@Getter
@Builder
public class CardTokenRawRequest {
  /** Full card number to be tokenized. */
  private final String cardNumber;

  /** Card expiration month (1–12). */
  private final Integer expirationMonth;

  /** Card expiration year (four-digit year, e.g. 2027). */
  private final Integer expirationYear;

  /** Security code (CVV/CVC) printed on the card. */
  private final String securityCode;

  /** Cardholder name and identification details. */
  private final CustomerCardCardholder cardholder;
}
