package com.mercadopago.resources.merchantorder;

import lombok.Getter;

/** Payer information of Merchant Order. */
@Getter
public class MerchantOrderPayer {
  /** Payer ID. */
  private Long id;

  /** Payer nickname. */
  private String nickname;
}
