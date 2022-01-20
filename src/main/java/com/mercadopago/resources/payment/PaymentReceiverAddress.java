package com.mercadopago.resources.payment;

import com.mercadopago.resources.common.Address;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/** PaymentReceiverAddress class. */
@EqualsAndHashCode(callSuper = true)
@Getter
public class PaymentReceiverAddress extends Address {
  /** State. */
  private String stateName;

  /** City. */
  private String cityName;

  /** Floor. */
  private String floor;

  /** Apartment. */
  private String apartment;
}
