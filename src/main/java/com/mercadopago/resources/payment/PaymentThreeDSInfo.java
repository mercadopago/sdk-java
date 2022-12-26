package com.mercadopago.resources.payment;

import lombok.Builder;
import lombok.Getter;

/** 3DS Info. */
@Getter
@Builder
public class PaymentThreeDSInfo {
  /** External Resource Url. */
  private final String externalResourceUrl;

  /** creq. */
  private final String creq;
}
