package com.mercadopago.resources.payment;

import com.mercadopago.resources.common.Address;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/** PaymentReceiverAddress class. */
@EqualsAndHashCode(callSuper = true)
@Getter
public class PaymentReceiverAddress extends Address {
  private String stateName;

  private String cityName;

  private String floor;

  private String apartment;
}
