package com.mercadopago.resources.merchantorder;

import lombok.Getter;

/** Seller information from Merchant Order. */
@Getter
public class MerchantOrderCollector {
  /** Collector ID. */
  private Long id;

  /** Collector nickname. */
  private String nickname;
}
