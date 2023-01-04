package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/** 3DS Info. */
@Getter
@Builder
public class PaymentThreeDSInfo {
  /** External Resource Url 3DS. */
  private final String externalResourceUrl;

  /** creq. */
  private final String creq;
}
