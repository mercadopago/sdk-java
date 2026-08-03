package com.mercadopago.resources.merchantorder;

import lombok.Getter;

/**
 * Represents country information within a merchant order receiver address in the MercadoPago API.
 *
 * <p>Contains the country identifier and name as part of the shipping destination address.
 *
 * @see MerchantOrderReceiverAddress
 */
@Getter
public class MerchantOrderReceiverAddressCountry {
  /** Unique identifier of the country. */
  private String id;

  /** Name of the country. */
  private String name;
}
