package com.mercadopago.resources.customer;

import lombok.Getter;

/**
 * Represents the cardholder information for a saved customer card in the MercadoPago API.
 *
 * <p>Contains the cardholder's name and identification document, which are used to validate card
 * ownership during payment processing.
 *
 * @see CustomerCard
 */
@Getter
public class CustomerCardCardholder {
  /** Full name of the cardholder as printed on the card. */
  private String name;

  /** Identification document of the cardholder. */
  private CustomerCardCardholderIdentification identification;
}
