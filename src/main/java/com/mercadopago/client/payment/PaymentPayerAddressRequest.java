package com.mercadopago.client.payment;

import com.mercadopago.client.common.AddressRequest;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Request object representing the payer's address in a payment request.
 * Extends {@link AddressRequest} with a federal-unit field for regions
 * that require state or province identification.
 */
@Getter
@SuperBuilder
public class PaymentPayerAddressRequest extends AddressRequest {

  /** Federal unit such as state or province (e.g. "SP", "BA"). */
  private String federalUnit;
}
