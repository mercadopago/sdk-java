package com.mercadopago.resources.customer;

import lombok.Data;

/** Security code of the customer card. */
@Data
public class CustomerCardSecurityCode {
  /** Length of security code. */
  private Integer length;

  /** Location of security code in the card. */
  private String cardLocation;
}
