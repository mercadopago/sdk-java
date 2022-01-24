package com.mercadopago.client.cardtoken;

import lombok.Builder;
import lombok.Getter;

/** Card data used in requests. */
@Builder
@Getter
public class CardTokenRequest {
  /** Id of the card */
  private String cardId;

  /** Id of the customer */
  private String customerId;

  /** Security code of the card */
  private String securityCode;
}
