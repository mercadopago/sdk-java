package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Data;

/** PaymentReceiverAddressRequest class. */
@Data
@Builder
public class PaymentReceiverAddressRequest {
  public String stateName;

  public String cityName;

  public String floor;

  public String apartment;

  public String zipCode;

  public String streetName;

  public String streetNumber;
}
