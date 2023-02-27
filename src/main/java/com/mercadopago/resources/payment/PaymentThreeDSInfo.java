package com.mercadopago.resources.payment;

import lombok.Getter;

/** 3DS Info. */
@Getter
public class PaymentThreeDSInfo {
  /** External Resource Url. */
  private String externalResourceUrl;

  /** creq. */
  private String creq;
}
