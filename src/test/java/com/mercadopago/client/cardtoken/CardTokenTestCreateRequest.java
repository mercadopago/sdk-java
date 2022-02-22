package com.mercadopago.client.cardtoken;

import lombok.Builder;

/** CardTokenTestCreateRequest class. */
@Builder
public class CardTokenTestCreateRequest {

  /** Card number. */
  public String cardNumber;

  /** Security code. */
  public String securityCode;

  /** Expiration month. */
  public int expirationMonth;

  /** Expiration year. */
  public int expirationYear;

  /** Card holder. */
  public CardTokenCardholderTestCreateRequest cardholder;
}
