package com.mercadopago.client.payment;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

/** PaymentAdditionalInfoRequest class. */
@Getter
@Builder
public class PaymentAdditionalInfoRequest {
  /** Ip address. */
  private final String ipAddress;

  /** Items. */
  private final List<PaymentItemRequest> items;

  /** Payer. */
  private final PaymentAdditionalInfoPayerRequest payer;

  /** Shipments. */
  private final PaymentShipmentsRequest shipments;

  /** Barcode. */
  private final PaymentAdditionalInfoBarcodeRequest barcode;
}
