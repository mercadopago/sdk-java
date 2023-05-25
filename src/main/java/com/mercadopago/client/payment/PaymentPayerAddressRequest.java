package com.mercadopago.client.payment;

import com.mercadopago.client.common.AddressRequest;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/** Payer's address information. */
@Getter
@SuperBuilder
public class PaymentPayerAddressRequest extends AddressRequest {
  /** Neighborhood. */
  private String neighborhood;

  /** City. */
  private String city;

  /** Federal Unit (e.g. state or province). */
  private String federalUnit;
}
