package com.mercadopago.resources.customer;

import lombok.Getter;

/** Cardholder details. */
@Getter
public class CustomerCardCardholder {
  /** Name of cardholder. */
  private String name;

  /** Identification of cardholder. */
  private CustomerCardCardholderIdentification identification;
}
