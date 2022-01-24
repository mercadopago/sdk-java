package com.mercadopago.client.merchantorder;

import lombok.Builder;
import lombok.Getter;

/** State information. */
@Getter
@Builder
public class MerchantOrderReceiverAddressStateRequest {
  /** State ID. */
  private final String id;

  /** State name. */
  private final String name;
}
