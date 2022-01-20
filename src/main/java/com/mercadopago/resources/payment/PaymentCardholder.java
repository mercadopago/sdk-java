package com.mercadopago.resources.payment;

import com.mercadopago.resources.common.Identification;
import lombok.Getter;

/** PaymentCardholder class. */
@Getter
public class PaymentCardholder {
  private String name;

  private Identification identification;
}
