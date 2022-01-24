package com.mercadopago.resources.merchantorder;

import lombok.Getter;

/** Seller information from Merchant Order. */
@Getter
public class MerchantOrderCollector {
  /** Collector ID. */
  private long id;

  /** Collector nickname. */
  private String nickname;
}
