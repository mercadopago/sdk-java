package com.mercadopago.resources.customer;

import lombok.Builder;
import lombok.Getter;

/**
 * Represents the issuer (financial institution) of a customer's saved card in the MercadoPago API.
 *
 * <p>Contains the issuer identifier and name, which identify the bank or financial institution
 * that issued the card.
 *
 * @see CustomerCard
 */
@Getter
@Builder
public class CustomerCardIssuer {
  /** Unique identifier of the card issuer. */
  private final String id;

  /** Name of the card issuer (e.g., bank name). */
  private final String name;
}
