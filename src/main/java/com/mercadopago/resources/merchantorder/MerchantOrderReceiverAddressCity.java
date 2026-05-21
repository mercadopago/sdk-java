package com.mercadopago.resources.merchantorder;

import lombok.Getter;

/**
 * Represents city information within a merchant order receiver address in the MercadoPago API.
 *
 * <p>Contains the city identifier and name as part of the shipping destination address.
 *
 * @see MerchantOrderReceiverAddress
 */
@Getter
public class MerchantOrderReceiverAddressCity {
  /** Unique identifier of the city. */
  private String id;

  /** Name of the city. */
  private String name;
}
