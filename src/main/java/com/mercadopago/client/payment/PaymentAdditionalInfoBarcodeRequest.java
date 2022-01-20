package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/** PaymentAdditionalInfoBarcodeRequest class. */
@Getter
@Builder
public class PaymentAdditionalInfoBarcodeRequest {
  /** Barcode type. */
  private final String type;

  /** Barcode content. */
  private final String content;

  /** Barcode width. */
  private final Double width;

  /** Barcode height. */
  private final Double height;
}
