package com.mercadopago.client.payment;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

/** PaymentAdditionalInfoRequest class. */
@Getter
@Builder
public class PaymentAdditionalInfoRequest {
  private final String ipAddress;

  private final List<PaymentItemRequest> items;

  private final PaymentAdditionalInfoPayerRequest payer;

  private final PaymentShipmentsRequest shipments;

  private final PaymentAdditionalInfoBarcodeRequest barcode;
}
