package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/**
 * Request object containing barcode information included in the additional-info
 * section of a payment. Describes the encoding type, content, and display
 * dimensions of a barcode associated with the payment.
 */
@Getter
@Builder
public class PaymentAdditionalInfoBarcodeRequest {
  /** Barcode encoding type (e.g. "Code128", "QR"). */
  private final String type;

  /** Encoded content of the barcode. */
  private final String content;

  /** Display width of the barcode in pixels. */
  private final Double width;

  /** Display height of the barcode in pixels. */
  private final Double height;
}
