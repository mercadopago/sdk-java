package com.mercadopago.resources.payment;

import com.mercadopago.resources.common.Address;
import lombok.Data;
import lombok.EqualsAndHashCode;

/** PaymentReceiverAddress class. */
@EqualsAndHashCode(callSuper = true)
@Data
public class PaymentReceiverAddress extends Address {
  private String stateName;

  private String cityName;

  private String floor;

  private String apartment;
}
