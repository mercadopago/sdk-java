package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/** PaymentAdditionalInfoBarcodeRequest class. */
@Getter
@Builder
public class PaymentAdditionalInfoBarcodeRequest {
  private final String type;

  private final String content;

  private final Double width;

  private final Double height;
}
