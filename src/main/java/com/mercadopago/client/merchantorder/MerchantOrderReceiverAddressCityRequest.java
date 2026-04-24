package com.mercadopago.client.merchantorder;

import lombok.Builder;
import lombok.Getter;

/**
 * Request DTO representing city information within a Merchant Order receiver address. Used as
 * part of the structured address breakdown for shipping destinations.
 *
 * @see <a href="https://www.mercadopago.com.br/developers/en/reference/merchant_orders">Merchant Orders API Reference</a>
 */
@Getter
@Builder
public class MerchantOrderReceiverAddressCityRequest {
  /** Unique identifier of the city. */
  private final String id;

  /** Display name of the city. */
  private final String name;
}
