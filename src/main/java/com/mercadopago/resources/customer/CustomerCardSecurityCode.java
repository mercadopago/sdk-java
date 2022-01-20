package com.mercadopago.resources.customer;

import lombok.Getter;

/** Security code of the customer card. */
@Getter
public class CustomerCardSecurityCode {
  /** Length of security code. */
  private Integer length;

  /** Location of security code in the card. */
  private String cardLocation;
}
