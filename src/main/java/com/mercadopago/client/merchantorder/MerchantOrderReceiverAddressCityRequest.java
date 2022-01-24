package com.mercadopago.client.merchantorder;

import lombok.Builder;
import lombok.Getter;

/** City information. */
@Getter
@Builder
public class MerchantOrderReceiverAddressCityRequest {
  /** City ID. */
  private final String id;

  /** City name. */
  private final String name;
}
