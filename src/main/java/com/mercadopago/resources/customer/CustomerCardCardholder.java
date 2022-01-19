package com.mercadopago.resources.customer;

import lombok.Data;

/** Cardholder details. */
@Data
public class CustomerCardCardholder {
  /** Name of cardholder. */
  private String name;

  /** Identification of cardholder. */
  private CustomerCardCardholderIdentification identification;
}
