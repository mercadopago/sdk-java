package com.mercadopago.client.cardtoken;

import lombok.Builder;
import lombok.Getter;

/** Card data used in requests. */
@Builder
@Getter
public class CardTokenRequest {
  /** Id of the card. */
  private final String cardId;

  /** Id of the customer. */
  private final String customerId;

  /** Security code of the card. */
  private final String securityCode;
}
