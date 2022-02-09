package com.mercadopago.client.merchantorder;

import lombok.Builder;
import lombok.Getter;

/** Payer information. */
@Getter
@Builder
public class MerchantOrderPayerRequest {
  /** Payer ID. */
  private final Long id;

  /** Payer nickname. */
  private final String nickname;
}
