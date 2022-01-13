package com.mercadopago.resources.payment;

import com.mercadopago.resources.common.Identification;
import lombok.Data;

/** PaymentCardholder class. */
@Data
public class PaymentCardholder {
  private String name;

  private Identification identification;
}
