package com.mercadopago.client.cardtoken;

import lombok.Builder;
import lombok.Getter;

/**
 * Request DTO used to create a card token for secure payment processing. Card tokens allow
 * sensitive card data to be handled in a PCI-compliant manner by replacing actual card details
 * with a temporary token that can be used in payment requests.
 *
 * @see <a href="https://www.mercadopago.com.br/developers/en/reference/card_tokens">Card Token API Reference</a>
 */
@Builder
@Getter
public class CardTokenRequest {
  /** Unique identifier of the saved card to be tokenized. */
  private final String cardId;

  /** Unique identifier of the customer who owns the card. */
  private final String customerId;

  /** Security code (CVV/CVC) printed on the card, required for token creation. */
  private final String securityCode;
}
