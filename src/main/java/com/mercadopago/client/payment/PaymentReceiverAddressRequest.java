package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/** PaymentReceiverAddressRequest class. */
@Getter
@Builder
public class PaymentReceiverAddressRequest {
  private final String stateName;

  private final String cityName;

  private final String floor;

  private final String apartment;

  private final String zipCode;

  private final String streetName;

  private final String streetNumber;
}
