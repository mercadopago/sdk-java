package com.mercadopago.resources.merchantorder;

import lombok.Getter;

/**
 * Represents state or province information within a merchant order receiver address in the
 * MercadoPago API.
 *
 * <p>Contains the state identifier and name as part of the shipping destination address.
 *
 * @see MerchantOrderReceiverAddress
 */
@Getter
public class MerchantOrderReceiverAddressState {
  /** Unique identifier of the state or province. */
  private String id;

  /** Name of the state or province. */
  private String name;
}
